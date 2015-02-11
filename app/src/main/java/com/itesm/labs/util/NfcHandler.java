package com.itesm.labs.util;

import android.nfc.NfcAdapter;
import android.nfc.Tag;

import java.lang.ref.WeakReference;

/**
 * Created by mgradob on 1/22/15.
 */
public class NfcHandler implements NfcAdapter.ReaderCallback {

    public interface UidCallback {
        void getUid(String uid);
    }

    private WeakReference<UidCallback> mCallback;

    public NfcHandler(UidCallback mCallback) {
        this.mCallback = new WeakReference<UidCallback>(mCallback);
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        String tagUid = bytesToHex(tag.getId());
        mCallback.get().getUid(tagUid);
        tagUid = "";
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
