package com.collegecode.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.collegecode.VITacademics.Home;
import com.collegecode.VITacademics.R;
import com.collegecode.VITacademics.VITxAPI;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.OnTaskComplete;
import com.collegecode.objects.QRCreator.Contents;
import com.collegecode.objects.QRCreator.QRCodeEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

/**
 * Created by saurabh on 5/11/14.
 */
public class QRCodeFragment extends Fragment{
    TextView lbl_token;
    TextView lbl_timerem;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qr_code,container, false);
        setHasOptionsMenu(true);
        imageView = (ImageView) v.findViewById(R.id.qrCode);
        lbl_token  = (TextView) v.findViewById(R.id.lbl_token);
        lbl_timerem = (TextView) v.findViewById(R.id.lbl_time_remaining);
        loadAll();
        return v;
    }

    private void loadAll(){
        int qrCodeDimention = 500;
        String code = ((Home) getActivity()).token;
        lbl_token.setText("PIN: " + code);
        lbl_timerem.setText(new DataHandler(getActivity()).getTokenExpiryTimeString());

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(code, null,
                Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    private ProgressDialog diag;

    private void QR_Refresh(){
        diag = new ProgressDialog(getActivity());
        diag.setMessage("Loading");
        diag.setCancelable(false);
        new DataHandler(getActivity()).saveToken("");
        diag.show();
        VITxAPI api = new VITxAPI(getActivity(), new OnTaskComplete() {
            @Override
            public void onTaskCompleted(Exception e, Object result) {
                if(e != null)
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                else{
                    Home h = (Home) getActivity();
                    h.token = (String) result;
                    loadAll();
                }
                diag.dismiss();
            }});
        api.getToken();
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_qrcode, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_fb_cancel:
                ((Home) getActivity()).selectItem_Async(3);
                return true;
            case R.id.menu_qr_refresh:
                QR_Refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
