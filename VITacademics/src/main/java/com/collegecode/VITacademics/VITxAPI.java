package com.collegecode.VITacademics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.collegecode.objects.DataHandler;
import com.collegecode.objects.OnTaskComplete;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by saurabh on 4/22/14.
 */

public class VITxAPI {
    private OnTaskComplete listner;
    private Context context;
    private DataHandler dat;

    private String CAPTCHA_URL;
    private String CAPTCHASUB_URL;
    private String ATTENDANCE_URL;



    public VITxAPI(Context context, OnTaskComplete listner){
        //Initialize with a result listner and context
        this.listner = listner;
        this.dat = new DataHandler(context);
        setUrls();
    }

    public void changeListner(OnTaskComplete listner){
        this.listner = listner;
    }

    private void setUrls(){
        String REG_NO = dat.getRegNo();
        String DOB = dat.getDOB();

        if(dat.isVellore()){
            CAPTCHA_URL = "http://vitacademicsrel.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsrel.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsrel.appspot.com/attj/" + REG_NO + "/" + DOB;
        }
        else{
            CAPTCHA_URL = "http://vitacademicsrelc.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsrelc.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsrelc.appspot.com/attj/" + REG_NO + "/" + DOB;
        }
    }

    public void loadAttendanceWithRegistrationNumber(){
        new loadAttendanceWithRegistrationNumber_Async().execute();
    }

    public void loadCaptchaBitmap(){
        new loadCaptchatBitmap_Async().execute();
    }

    private HttpResponse getResponse(String url) throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        return client.execute(request);
    }

    private class loadCaptchatBitmap_Async extends AsyncTask<Void,Void,Bitmap>{
        private Exception e = null;

        public byte[] convertInputStreamToByteArray(InputStream is) throws IOException
        {
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while(result !=-1)
            {
                byte b = (byte)result;
                buf.write(b);
                result = bis.read();
            }
            return buf.toByteArray();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap temp = null;
            try
            {

                HttpEntity entity= getResponse(CAPTCHA_URL).getEntity();
                byte [] content = convertInputStreamToByteArray(entity.getContent());
                temp = BitmapFactory.decodeByteArray(content, 0, content.length);
            }catch (Exception ex){this.e = ex;}

            return temp;
        }

        protected void onPostExecute(Bitmap result){
            listner.onTaskCompleted(e, result);
        }

    }

    private class loadAttendanceWithRegistrationNumber_Async extends AsyncTask<Void,Void,Void>{
        private Exception e = null;

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(null, "Hello");
        }
    }
}
