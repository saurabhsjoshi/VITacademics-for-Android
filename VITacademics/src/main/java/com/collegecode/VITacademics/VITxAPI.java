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
    private DataHandler dat;

    private String CAPTCHA_URL;
    private String CAPTCHALESS_URL;
    private String TIMETABLE_URL;
    private String CAPTCHASUB_URL;
    private String ATTENDANCE_URL;
    private String MARKS_URL;

    public String Captcha;

    public boolean isDev = true;

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

        if(isDev)
        {
            CAPTCHALESS_URL = "http://www.vitacademicsdev.appspot.com/captchaless/" + REG_NO + "/" + DOB;
            CAPTCHA_URL = "http://vitacademicsdev.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsdev.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsdev.appspot.com/attj/" + REG_NO + "/" + DOB;
            TIMETABLE_URL = "http://vitacademicstokensystem.appspot.com/gettimetable/" + REG_NO + "/" + DOB;
            MARKS_URL = "http://www.vitacademicsdev.appspot.com/marks/" + REG_NO + "/" + DOB;
            CAPTCHASUB_URL = "http://www.vitacademicsdev.appspot.com/captchasub/" + REG_NO + "/" + DOB + "/" + Captcha;
            return;
        }
        if(dat.isVellore()){
            CAPTCHALESS_URL = "http://www.vitacademicsrel.appspot.com/captchaless/" + REG_NO + "/" + DOB;
            CAPTCHA_URL = "http://vitacademicsrel.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsrel.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsrel.appspot.com/attj/" + REG_NO + "/" + DOB;
            TIMETABLE_URL = "http://vitacademicstokensystem.appspot.com/gettimetable/" + REG_NO + "/" + DOB;
            MARKS_URL = "http://www.vitacademicsrel.appspot.com/marks/" + REG_NO + "/" + DOB;
            CAPTCHASUB_URL = "http://www.vitacademicsrel.appspot.com/captchasub/" + REG_NO + "/" + DOB + "/" + Captcha;
        }
        else{
            CAPTCHALESS_URL = "http://www.vitacademicsrelc.appspot.com/captchaless/" + REG_NO + "/" + DOB;
            CAPTCHA_URL = "http://vitacademicsrelc.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsrelc.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsrelc.appspot.com/attj/" + REG_NO + "/" + DOB;
            TIMETABLE_URL = "http://vitacademicstokensystemc.appspot.com/gettimetable/" + REG_NO + "/" + DOB;
            MARKS_URL = "http://www.vitacademicsrelc.appspot.com/marks/" + REG_NO + "/" + DOB;
            CAPTCHASUB_URL = "http://www.vitacademicsrelc.appspot.com/captchasub/" + REG_NO + "/" + DOB + "/" + Captcha;
        }
    }

    public void loadAttendanceWithRegistrationNumber(){new loadAttendanceWithRegistrationNumber_Async().execute();}
    public void loadTimeTable(){new loadTimTable_Async().execute();}
    public void loadMarks(){new loadMarks_Async().execute();}
    public void CaptchaLessLoad(){new captchaLessLoad_Async().execute();}
    public void loadCaptchaBitmap(){
        new loadCaptchatBitmap_Async().execute();
    }
    public void submitCaptcha(){setUrls(); new submitCaptcha_Async().execute();}

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
                HttpResponse res = getResponse(CAPTCHALESS_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                String result = EntityUtils.toString((res).getEntity());

                if(result.equals("success"))
                    return null;
                else
                    e =  new Exception("Incorrect Credentials. Please try again");

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
                HttpResponse res = getResponse(CAPTCHA_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                HttpEntity entity= res.getEntity();

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
                HttpResponse res = getResponse(TIMETABLE_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                String result = EntityUtils.toString(res.getEntity());
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
                HttpResponse res = getResponse(MARKS_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                String result = EntityUtils.toString(res.getEntity());
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
                HttpResponse res = getResponse(ATTENDANCE_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                String result = EntityUtils.toString(res.getEntity());
                if(result.contains("valid"))
                    dat.saveJSON(result);
                else if(result.equals("timedout"))
                    e = new Exception("needref");
                else
                    e = new Exception("Error! Could not load attendance!");
            }catch (Exception e1){e = new Exception("Oops! Something went wrong. Check your network!");}
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "Done");
        }
    }

    private class submitCaptcha_Async extends AsyncTask<Void,Void,Void>{
        private Exception e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HttpResponse res = getResponse(CAPTCHASUB_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                String result = EntityUtils.toString(res.getEntity());

                if(result.contains("success"))
                    return null;
                else if(result.equals("timedout"))
                    e = new Exception("needref");
                else if(result.equals("captchaerror"))
                    e = new Exception("cape");
                else
                    e = new Exception("Error! Could not load attendance!");
            }catch (Exception e1){e1.printStackTrace(); e = new Exception("Oops! Something went wrong. Check your network!");}
            return null;
        }
        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "Done");
        }
    }
}
