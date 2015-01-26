package com.itesm.labs.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;

/**
 * Created by mgradob on 1/22/15.
 */
public class NfcHandler {

    private NfcAdapter mNfcAdapter;

    private Activity mActivity;
    private IntentFilter mIntentFilterArray[];
    private PendingIntent mPendingIntent;
    private String cardTypeListArray[][];

    public NfcHandler(Activity mActivity) {
        super();
        this.mActivity = mActivity;

        mNfcAdapter = NfcAdapter.getDefaultAdapter(mActivity.getApplicationContext());
        mPendingIntent = PendingIntent.getActivity(
                mActivity,
                0,
                new Intent(
                        mActivity,
                        mActivity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0);

        IntentFilter nDef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        mIntentFilterArray = new IntentFilter[] { nDef };
        cardTypeListArray = new String[][] { new String[] {NfcA.class.getName() } };
    }

    public void enableForeground() {
        mNfcAdapter.enableForegroundDispatch(
                mActivity,
                mPendingIntent,
                mIntentFilterArray,
                cardTypeListArray);
    }

    public void disableForeground() {
        mNfcAdapter.disableForegroundDispatch(mActivity);
    }

    public NfcAdapter getmNfcAdapter() {
        return mNfcAdapter;
    }

    /**
     * Convenience method to convert a byte array to a hex string.
     *
     * @param data the byte[] to convert
     * @return String the converted byte[]
     */
    public static String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(byteToHex(data[i]).toUpperCase());
        }
        return (buf.toString());
    }

    /**
     * method to convert a byte to a hex string.
     *
     * @param data the byte to convert
     * @return String the converted byte
     */
    public static String byteToHex(byte data) {
        StringBuffer buf = new StringBuffer();
        buf.append(toHexChar((data >>> 4) & 0x0F));
        buf.append(toHexChar(data & 0x0F));
        return buf.toString();
    }

    /**
     * Convenience method to convert an int to a hex char.
     *
     * @param i the int to convert
     * @return char the converted char
     */
    public static char toHexChar(int i) {
        if ((0 <= i) && (i <= 9)) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + (i - 10));
        }
    }
}
