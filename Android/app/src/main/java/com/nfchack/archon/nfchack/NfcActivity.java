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

import butterknife.Bind;

public class NfcActivity extends AppCompatActivity {

    @Bind(R.id.list)
    RecyclerView List;

    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProductAdapter(this);

        //TODO: call api and retreive all products associated with order
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals("application/" + getPackageName())) {
            // Read the first record which contains the NFC data
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefRecord relayRecord = ((NdefMessage)rawMsgs[0]).getRecords()[0];
            String nfcData = new String(relayRecord.getPayload());

            // Display the data on the tag
            Toast.makeText(this, nfcData, Toast.LENGTH_SHORT).show();

            // Do other stuff with the data...
        }
    }
}
