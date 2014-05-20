package com.karthikb351.vitinfo2.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.VITxAPI;
import com.karthikb351.vitinfo2.objects.CaptchaDialogListener;
import com.karthikb351.vitinfo2.objects.OnTaskComplete;

;

/**
 * Created by saurabh on 5/2/14.
 */
public class CaptchaDialog extends Dialog implements View.OnClickListener{

    private Activity context;
    private CaptchaDialogListener listener;
    private Button yes, no, cap_ref;
    private ImageView img_captcha;
    private EditText txt_captcha;
    private ProgressDialog pdia;
    private boolean isCancelled = false;
    private Bitmap b_cap;
    private VITxAPI cont;
    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_captcha);

        this.setCancelable(false);
        this.setTitle("Captcha");

        yes = (Button) findViewById(R.id.btn_ok);
        no = (Button) findViewById(R.id.btn_cancel);
        cap_ref = (Button) findViewById(R.id.captcha_refresh);


        img_captcha = (ImageView) findViewById(R.id.captcha_img);
        txt_captcha = (EditText) findViewById(R.id.captcha_edittext);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        cap_ref.setOnClickListener(this);

        cap_ref.setEnabled(false);


        pdia = new ProgressDialog(context);
        pdia.setMessage("Fetching Captcha...");
        pdia.setCancelable(true);

        pdia.setCanceledOnTouchOutside(false);
        pdia.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                isCancelled = true;
            }
        });
        pdia.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        cont = new VITxAPI(context, new OnTaskComplete() {
            @Override
            public void onTaskCompleted(Exception e, Object result) {
                if(e != null)
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                else{

                    if(!isCancelled){
                        Display display = context.getWindowManager().getDefaultDisplay();
                        int width=(int)(display.getWidth()*0.6);
                        int height= (int)(width*25/130);
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                        parms.setMargins(10, 10, 10, 10);
                        img_captcha.setLayoutParams(parms);
                        b_cap = (Bitmap) result;
                        img_captcha.setImageBitmap(b_cap);
                    }
                    else
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();

                }
                cap_ref.setEnabled(true);
                pdia.dismiss();
            }
        });


    }

    public void onWindowFocusChanged (boolean hasFocus){
        if(hasFocus && first)
        {
            first = false;
            pdia.show();
            cont.loadCaptchaBitmap();
        }
    }

    public CaptchaDialog(Activity a, CaptchaDialogListener listener) {
        super(a);
        this.context = a;
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_ok:
                listener.onTaskCompleted(txt_captcha.getText().toString(), true);
                dismiss();
                break;

            case R.id.btn_cancel:
                listener.onTaskCompleted("", false);
                dismiss();
                break;

            case R.id.captcha_refresh:
                pdia.show();
                cap_ref.setEnabled(false);
                cont.loadCaptchaBitmap();
                break;
        }

    }
}
