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
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by saurabh on 4/22/14.
 * File to handle interaction with VITacademics server async
 */

public class VITxAPI {
    private OnTaskComplete listner;
    private Context context;
    private DataHandler dat;

    private String CAPTCHA_URL;
    private String CAPTCHALESS_URL;
    private String TIMETABLE_URL;
    private String CAPTCHASUB_URL;
    private String ATTENDANCE_URL;
    private String MARKS_URL;

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
        String DOB = dat.getDOBString();

        if(dat.isVellore()){
            CAPTCHALESS_URL = "http://www.vitacademicsrel.appspot.com/captchaless/" + REG_NO + "/" + DOB;
            CAPTCHA_URL = "http://vitacademicsrel.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsrel.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsrel.appspot.com/attj/" + REG_NO + "/" + DOB;
            TIMETABLE_URL = "http://vitacademicstokensystem.appspot.com/gettimetable/" + REG_NO + "/" + DOB;
            MARKS_URL = "http://www.vitacademicsrel.appspot.com/marks/" + REG_NO + "/" + DOB;
        }
        else{
            CAPTCHALESS_URL = "http://www.vitacademicsrelc.appspot.com/captchaless/" + REG_NO + "/" + DOB;
            CAPTCHA_URL = "http://vitacademicsrelc.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsrelc.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsrelc.appspot.com/attj/" + REG_NO + "/" + DOB;
            TIMETABLE_URL = "http://vitacademicstokensystemc.appspot.com/gettimetable/" + REG_NO + "/" + DOB;
            MARKS_URL = "http://www.vitacademicsrelc.appspot.com/marks/" + REG_NO + "/" + DOB;
        }
    }

    public void loadAttendanceWithRegistrationNumber(){new loadAttendanceWithRegistrationNumber_Async().execute();}
    public void loadTimeTable(){new loadTimTable_Async().execute();}
    public void loadMarks(){new loadMarks_Async().execute();}
    public void CaptchaLessLoad(){new captchaLessLoad_Async().execute();}
    public void loadCaptchaBitmap(){
        new loadCaptchatBitmap_Async().execute();
    }

    private HttpResponse getResponse(String url) throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        return client.execute(request);
    }

    private class captchaLessLoad_Async extends AsyncTask<Void,Void,Void>{
        Exception e = null;
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                String result = EntityUtils.toString(getResponse(CAPTCHALESS_URL).getEntity());

                if(result.equals("success"))
                    return null;
                else
                    throw new Exception("Incorrect Credentials. Please try again");
            }catch (Exception e1){e = new Exception("Oops! Something went wrong. Check your network!");}
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "Done");
        }
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
            }catch (Exception ex){e = new Exception("Oops! Something went wrong. Check your network!");}

            return temp;
        }

        protected void onPostExecute(Bitmap result){
            listner.onTaskCompleted(e, result);
        }
    }

    private class loadTimTable_Async extends AsyncTask<Void,Void,Void>{
        private Exception e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String result = EntityUtils.toString(getResponse(TIMETABLE_URL).getEntity());
                dat.saveTimeTable(result);
            }catch (Exception e1){e = new Exception("Oops! Something went wrong. Check your network!");}
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "Done");
        }
    }

    private class loadMarks_Async extends AsyncTask<Void,Void,Void>{
        private Exception e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String result = EntityUtils.toString(getResponse(MARKS_URL).getEntity());
                dat.saveMarks(result);
            }catch (Exception e1){e = new Exception("Oops! Something went wrong. Check your network!");}
            return null;
        }
        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "Done");
        }
    }

    private class loadAttendanceWithRegistrationNumber_Async extends AsyncTask<Void,Void,Void>{
        private Exception e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String result = EntityUtils.toString(getResponse(ATTENDANCE_URL).getEntity());
                if(result.contains("valid"))
                    dat.saveJSON(result);
                else
                    throw new Exception("Error! Could not load attendance!");
            }catch (Exception e1){e = new Exception("Oops! Something went wrong. Check your network!");}
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "Done");
        }
    }
}
