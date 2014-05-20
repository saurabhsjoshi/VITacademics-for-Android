package com.karthikb351.vitinfo2.PrefsUI;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

;

/**
 * Created by saurabh on 4/30/14.
 */
public class PrefsOpen extends DialogPreference {
    TextView lbl_dat;
    Context context;

    public PrefsOpen(Context context, AttributeSet attrs){
        super(context, attrs);
        setDialogLayoutResource(R.layout.prefdialog_open);
        this.context = context;
    }

    @Override
    public void onBindDialogView(View view){
        super.onBindDialogView(view);
        lbl_dat = (TextView) view.findViewById(R.id.lbl_open);

        (view.findViewById(R.id.pref_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        new loadAll().execute();
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle("Open Source Licenses");
        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
        super.onPrepareDialogBuilder(builder);
    }

    private class loadAll extends AsyncTask<Void,Void,Void>{
        String final_string = "";

        private String readRawTextFile(Context ctx, int resId)
        {
            InputStream inputStream = ctx.getResources().openRawResource(resId);

            InputStreamReader inputreader = new InputStreamReader(inputStream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            StringBuilder text = new StringBuilder();

            try {
                while (( line = buffreader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
            } catch (IOException e) {
                return null;
            }
            return text.toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Field[] fields=R.raw.class.getFields();
            for (Field field : fields) {
                try {
                    if(!field.getName().equals("beep")){
                        final_string += field.getName() + ": \n";
                        final_string += readRawTextFile(context, field.getInt(field));
                        final_string += "\n\n";
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void voids){
            lbl_dat.setText(final_string);
        }
    }


}
