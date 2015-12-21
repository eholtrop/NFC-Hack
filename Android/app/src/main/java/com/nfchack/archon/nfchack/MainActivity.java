package com.nfchack.archon.nfchack;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text)
    public TextView mText;

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
    }

    @Override
    protected void onNewIntent(Intent intent) {

        switch(intent.getAction()) {
            case NfcAdapter.ACTION_TAG_DISCOVERED:

                if(intent.hasExtra(NfcAdapter.EXTRA_ID)) {

                } else if(intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
                    Toast.makeText(this, intent.getStringExtra(NfcAdapter.EXTRA_TAG), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(this, "NFC Intent Received", Toast.LENGTH_LONG).show();

                break;
        }


        super.onNewIntent(intent);
    }

    private void formatTag(Tag tag, NdefMessage message) {
            try {
                NdefFormatable formatable = NdefFormatable.get(tag);

                if(formatable == null) {
                    Toast.makeText(this, "Tag invalid", Toast.LENGTH_SHORT).show();
                }

                formatable.connect();
                formatable.format(message);
                formatable.close();

            } catch (Exception e) {
                Log.e("formatTag", e.getMessage());
            }
    }

    private void writeNfcMessage(Tag tag, NdefMessage message) {
        try{

            if(tag == null) return;
        } catch(Exception e) {
            Log.e("writeMEssage", e.getMessage());

        }
    }

    private void createNdefMessage(String content) {
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

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
            Toast.makeText(this, "NFC tag read", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mNfcAdapter.disableForegroundDispatch(this);
    }

    private void processIntent(Intent i) {

    }
}
