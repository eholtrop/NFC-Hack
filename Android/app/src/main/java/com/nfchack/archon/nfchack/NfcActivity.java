package com.nfchack.archon.nfchack;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.nfchack.archon.nfchack.adapters.ProductAdapter;
import com.nfchack.archon.nfchack.models.NfcRequest;
import com.nfchack.archon.nfchack.services.Api;
import com.nfchack.archon.nfchack.viewModels.NfcViewModel;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class NfcActivity extends AppCompatActivity {


    private ProductAdapter mAdapter;
    private NfcViewModel ViewModel = new NfcViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: call api and retreive all products associated with order

        ViewModel.Request.subscribe(new Action1<String>() {
            @Override
            public void call(String request) {
                Api.GET(NfcActivity.this, request, new Api.Listener() {
                    @Override
                    public void OnGetResponse(JSONObject response) {
                        Intent i = new Intent(NfcActivity.this, OrderActivity.class);
                        i.putExtra("order_json", response.toString());
                        startActivity(i);
                    }

                    @Override
                    public void OnPostResponse(String response) {

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals("application/" + getPackageName())) {
            // Read the first record which contains the NFC data
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefRecord relayRecord = ((NdefMessage)rawMsgs[0]).getRecords()[0];
            ViewModel.setRequest(new String(relayRecord.getPayload()));


        }
    }
}
