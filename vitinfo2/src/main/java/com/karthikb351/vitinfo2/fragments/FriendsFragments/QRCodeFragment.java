package com.karthikb351.vitinfo2.fragments.FriendsFragments;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.karthikb351.vitinfo2.Home;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.Objects.Share;
import com.karthikb351.vitinfo2.api.Objects.VITxApi;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.QRCreator.Contents;
import com.karthikb351.vitinfo2.objects.QRCreator.QRCodeEncoder;

;

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
        lbl_timerem.setText(DataHandler.getInstance(getActivity()).getTokenExpiryTimeString());

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
        final Share tempShare = DataHandler.getInstance(getActivity()).getShareJSON();

        //Change to oldddd date
        DataHandler.getInstance(getActivity()).getShareJSON().setIssued("1900-01-01T14:45:21.455Z");

        diag.show();

        VITxApi.getInstance(getActivity()).getToken(new VITxApi.onTaskCompleted() {
            @Override
            public void onCompleted(Object result, Exception e) {
                if(e != null){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    //Set Back to original date!
                    DataHandler.getInstance(getActivity()).setShareJSON(tempShare);
                }
                else{
                    Home h = (Home) getActivity();
                    h.token = (String) result;
                    loadAll();
                }
                diag.dismiss();
            }
        });
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
