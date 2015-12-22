package com.nfchack.archon.nfchack;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nfchack.archon.nfchack.models.NfcRequest;
import com.nfchack.archon.nfchack.models.RequestType;
import com.nfchack.archon.nfchack.services.Api;
import com.nfchack.archon.nfchack.utils.NfcUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text)
    public TextView mText;
    @Bind(R.id.write)
    EditText Write;

    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);


        if (!mNfcAdapter.isEnabled()) {
            mText.setText("NFC is disabled.");
        } else {
            mText.setText("NFC is enabled");
        }

        Api.POST(this);
    }


    @Override
    protected void onNewIntent(Intent intent) {

        String s = intent.getAction();
        if (s.equals(NfcAdapter.ACTION_NDEF_DISCOVERED) || s.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String nfcMessage = getWriteText();
            Toast.makeText(this, "NFC Found, Writing Data: " + getWriteText(), Toast.LENGTH_LONG).show();
            boolean result = NfcUtils.write(this, tag, nfcMessage);

            if (result) {
                Toast.makeText(this, "NFC Write Succeeded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "NFC Write Failed", Toast.LENGTH_LONG).show();
            }


        }


        super.onNewIntent(intent);
    }

    public void parseTag() {

    }

    public String getWriteText() {
        return Write.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};

        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();

        mNfcAdapter.disableForegroundDispatch(this);
    }
}
