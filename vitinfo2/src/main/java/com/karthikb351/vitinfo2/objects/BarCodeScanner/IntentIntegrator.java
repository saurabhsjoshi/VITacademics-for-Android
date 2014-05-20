package com.karthikb351.vitinfo2.objects.BarCodeScanner;

import android.app.Activity;
import android.content.Intent;

/**
 * @author Jim.H
 */
public final class IntentIntegrator {

    public static final int REQUEST_CODE = 0xFFFE;

    private IntentIntegrator() {
    }

    /**
     * start to scan with default formats and character set.
     * 
     * @param activity
     * see #initiateScan(android.app.Activity, String, String)
     */
    public static void initiateScan(Activity activity) {
        initiateScan(activity, null, null, null);
    }

    /**
     * start to scan with default formats and character set.
     * 
     * @param activity
     * @param config can be null
     * see #initiateScan(android.app.Activity, String, String)
     */
    public static void initiateScan(Activity activity, ZXingLibConfig config) {
        initiateScan(activity, null, null, config);
    }

    /**
     * @param activity parent activity
     * @param scanFormatsString barcodeFormats separated by comma. Null value is
     *            accepted.<br />
     *            <ul>
     *            <li><strong>AZTEC</strong> Aztec 2D barcode format.</li>
     *            <li><strong>CODABAR</strong> CODABAR 1D format.</li>
     *            <li><strong>CODE_39</strong> Code 39 1D format.</li>
     *            <li><strong>CODE_93</strong> Code 93 1D format.</li>
     *            <li><strong>CODE_128</strong> Code 128 1D format.</li>
     *            <li><strong>DATA_MATRIX</strong> Data Matrix 2D barcode
     *            format.</li>
     *            <li><strong>EAN_8</strong> EAN-8 1D format.</li>
     *            <li><strong>EAN_13</strong> EAN-13 1D format.</li>
     *            <li><strong>ITF</strong> ITF (Interleaved Two of Five) 1D
     *            format.</li>
     *            <li><strong>PDF_417</strong> PDF417 format.</li>
     *            <li><strong>QR_CODE</strong> QR Code 2D barcode format.</li>
     *            <li><strong>RSS_14</strong> RSS 14</li>
     *            <li><strong>RSS_EXPANDED</strong> RSS EXPANDED</li>
     *            <li><strong>UPC_A</strong> UPC-A 1D format.</li>
     *            <li><strong>UPC_E</strong> UPC-E 1D format.</li>
     *            <li><strong>UPC_EAN_EXTENSION</strong> UPC/EAN extension
     *            format. Not a stand-alone format.</li>
     *            </ul>
     * @param characterSet e.g. "utf-8"... or null
     * @param config can be null
     * @see com.google.zxing.BarcodeFormat
     */
    public static void initiateScan(Activity activity, String scanFormatsString,
                                    String characterSet, ZXingLibConfig config) {
        Intent intent = new Intent(activity, CaptureActivity.class);
        intent.putExtra(Intents.Scan.FORMATS, scanFormatsString);
        intent.putExtra(Intents.Scan.CHARACTER_SET, characterSet);
        intent.putExtra(ZXingLibConfig.INTENT_KEY, config);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public static IntentResult parseActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String contents = intent.getStringExtra(Intents.Scan.RESULT);
                String formatName = intent.getStringExtra(Intents.Scan.RESULT_FORMAT);
                return new IntentResult(contents, formatName);
            } else {
                return new IntentResult(null, null);
            }
        }
        return null;
    }
}
