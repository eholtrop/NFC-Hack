package com.nfchack.archon.nfchack.utils;

import android.content.Context;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;

import com.nfchack.archon.nfchack.R;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Evan on 12/21/2015.
 */
public class NfcUtils {

    public static boolean write(Context context, Tag tag, String data) {
        NdefRecord appRecord = NdefRecord.createApplicationRecord(context.getPackageName());

        NdefRecord relayRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                new String("application/" + context.getPackageName())
                        .getBytes(Charset.forName("US-ASCII")),
                null, data.getBytes());

        // Complete NDEF message with both records
        NdefMessage message = new NdefMessage(new NdefRecord[] {relayRecord, appRecord});

        try {
            // If the tag is already formatted, just write the message to it
            Ndef ndef = Ndef.get(tag);
            if(ndef != null) {
                ndef.connect();

                // Make sure the tag is writable
                if(!ndef.isWritable()) {
                    Log.e("NFC", "NFC is read only");
                    return false;
                }

                // Check if there's enough space on the tag for the message
                int size = message.toByteArray().length;
                if(ndef.getMaxSize() < size) {
//                    DialogUtils.displayErrorDialog(context, R.string.nfcBadSpaceErrorTitle, R.string.nfcBadSpaceError);
                    return false;
                }

                try {
                    // Write the data to the tag
                    ndef.writeNdefMessage(message);
                    return true;
                } catch (TagLostException tle) {
                    return false;
                } catch (IOException ioe) {
                    return false;
                } catch (FormatException fe) {
                    return false;
                }
                // If the tag is not formatted, format it with the message
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if(format != null) {
                    try {
                        format.connect();
                        format.format(message);

//                        DialogUtils.displayInfoDialog(context, R.string.nfcWrittenTitle, R.string.nfcWritten);
                        return true;
                    } catch (TagLostException tle) {
//                        DialogUtils.displayErrorDialog(context, R.string.nfcTagLostErrorTitle, R.string.nfcTagLostError);
                        return false;
                    } catch (IOException ioe) {
//                        DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                        return false;
                    } catch (FormatException fe) {
//                        DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                        return false;
                    }
                } else {
//                    DialogUtils.displayErrorDialog(context, R.string.nfcNoNdefErrorTitle, R.string.nfcNoNdefError);
                    return false;
                }
            }
        } catch(Exception e) {
            Log.e("NFC Error", e.getMessage());
        }

        return false;
    }

    private String getNfcTag(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();
    }
}
