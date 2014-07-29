package com.karthikb351.vitinfo2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.Friend;
import com.karthikb351.vitinfo2.objects.OnTaskComplete;
import com.koushikdutta.ion.Ion;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
    private String TOKEN_URL;
    private String TOKENSUB_URL;
    private String FRIEND_TIMETABLE_URL;
    private String SERVER_STATUS;

    public String Captcha;
    public String Token;

    public String Friend_regno;
    public String Friend_dob;

    public boolean isDev = false;

    public VITxAPI(Context context, OnTaskComplete listner){
        //Initialize with a result listner and context
        this.listner = listner;
        this.dat = DataHandler.getInstance(context);
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
            TOKEN_URL = "http://vitacademicstokensystem.appspot.com/getnewtoken/" + REG_NO + "/" + DOB;
            TOKENSUB_URL = "http://vitacademicstokensystem.appspot.com/accesstoken/" + Token;
            FRIEND_TIMETABLE_URL = "http://vitacademicstokensystem.appspot.com/gettimetable/" + Friend_regno + "/" + Friend_dob;
            SERVER_STATUS = "http://vitacademicsrel.appspot.com/status";
        }
        else{
            CAPTCHALESS_URL = "http://www.vitacademicsrelc.appspot.com/captchaless/" + REG_NO + "/" + DOB;
            CAPTCHA_URL = "http://vitacademicsrelc.appspot.com/captcha/" + REG_NO;
            CAPTCHASUB_URL = "http://vitacademicsrelc.appspot.com/captchasub/" + REG_NO + "/" + DOB;
            ATTENDANCE_URL = "http://vitacademicsrelc.appspot.com/attj/" + REG_NO + "/" + DOB;
            TIMETABLE_URL = "http://vitacademicstokensystemc.appspot.com/gettimetable/" + REG_NO + "/" + DOB;
            MARKS_URL = "http://www.vitacademicsrelc.appspot.com/marks/" + REG_NO + "/" + DOB;
            CAPTCHASUB_URL = "http://www.vitacademicsrelc.appspot.com/captchasub/" + REG_NO + "/" + DOB + "/" + Captcha;
            TOKEN_URL = "http://vitacademicstokensystemc.appspot.com/getnewtoken/" + REG_NO + "/" + DOB;
            TOKENSUB_URL = "http://vitacademicstokensystemc.appspot.com/accesstoken/" + Token;
            FRIEND_TIMETABLE_URL = "http://vitacademicstokensystemc.appspot.com/gettimetable/" + Friend_regno + "/" + Friend_dob;
            SERVER_STATUS = "http://vitacademicsrelc.appspot.com/status";
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
    public void submitToken(){setUrls(); new submitToken_Async().execute();}
    public void getToken(){new getToken_Async().execute();}
    public void AddFriendwithCredentials(){setUrls(); new AddFriendwithCredentials_Async().execute();}
    public void saveServerStatus(){new saveServerStatus_Async().execute();}

    private HttpResponse getResponse(String url) throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        return client.execute(request);
    }

    private class saveServerStatus_Async extends AsyncTask<Void,Void,Void>{
        boolean ret = false;

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                String res = EntityUtils.toString(getResponse(SERVER_STATUS).getEntity());
                JSONObject obj = new JSONObject(res);

                int ver = Integer.parseInt(obj.getString("msg_no"));
                String saved = dat.getServerStatus();

                if(!saved.equals("")){
                    int sv = Integer.parseInt(new JSONObject(saved).getString("msg_no"));
                    if(sv != ver)
                        ret = true;
                }
                else
                    ret = true;

                if(ret)
                    dat.saveServerStatus(res);



            }catch (Exception ignore){}

            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(null, ret);
        }
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
                //Check if really timetable or just blank text.
                try{
                    new JSONObject(result);
                }catch (JSONException e1){
                    e = new Exception("Oops! Could not parse Timetable. Try again!");
                    return null;
                }
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

    private class getToken_Async extends AsyncTask <Void,Void,Void>{
        Exception e = null;
        String token = "";

        @Override
        protected Void doInBackground(Void... voids) {
           try{
               token = dat.getToken();
               if(token.equals("expired")){
                   HttpResponse res = getResponse(TOKEN_URL);

                   if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                   {
                       e = new Exception("Our servers are overloaded! Please try again later");
                       return null;
                   }

                   String result = EntityUtils.toString(res.getEntity());
                   dat.saveToken(result);
                   token = dat.getToken();
               }
           }catch (Exception e1){
               e1.printStackTrace();
               e = new Exception("Oops! Something went wrong. Check your network!");
           }
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, token);
        }

    }

    private class submitToken_Async extends AsyncTask <Void,Void,Void>{
        Exception e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                HttpResponse res = getResponse(TOKENSUB_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                String result = EntityUtils.toString(res.getEntity());
                JSONObject root = new JSONObject(result);
                if(root.getString("status").equals("success")){
                    Friend f = new Friend();
                    f.regno = root.getString("regno");
                    f.dob = root.getString("dob");
                    f.title = root.getString("regno");

                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    ParseUser u = (query.whereEqualTo("username",f.regno)).getFirst();

                    if(u.getString("isSignedIn").equals("true")){
                        f.isFb = true;
                        f.fbId = u.getString("facebookID");
                        f.title = u.getString("facebookName");

                        Ion.with(dat.context)
                                .load("http://graph.facebook.com/" + f.fbId + "/picture?type=large")
                                .write(new File(dat.context.getCacheDir().getPath() + "/" + f.fbId + ".jpg"))
                                .get();

                    }
                    //Get the timetable
                    f.timetable = EntityUtils.toString(getResponse("http://vitacademicstokensystem.appspot.com/gettimetable/" + f.regno + "/" + f.dob).getEntity());
                    try {
                        new JSONObject(f.timetable);
                    }catch (JSONException e1){
                        e = new Exception("Could not parse friends timetable. Please try again!");
                        return null;
                    }
                    //Save friend to memory!
                    dat.addFriend(f);
                }
                else{
                    e = new Exception("Oops! Looks like the token is incorrect or expired.");
                }

            }catch (Exception e1){
                e1.printStackTrace();
                e = new Exception("Oops! Something went wrong. Check your network!");
            }
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "done");
        }

    }

    private class AddFriendwithCredentials_Async extends AsyncTask <Void,Void,Void>{
        Exception e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                HttpResponse res = getResponse(FRIEND_TIMETABLE_URL);

                if(res.getStatusLine().getStatusCode() == 403 || res.getStatusLine().getStatusCode() == 503)
                {
                    e = new Exception("Our servers are overloaded! Please try again later");
                    return null;
                }

                String result = EntityUtils.toString(res.getEntity());

                Friend f = new Friend();
                f.regno = Friend_regno;
                f.dob = Friend_dob;
                f.title = Friend_regno;

                ParseQuery<ParseUser> query = ParseUser.getQuery();
                ParseUser u = (query.whereEqualTo("username",f.regno)).getFirst();

                String isIt = u.getString("isSignedIn");
                if(isIt != null && isIt.equals("true")){
                    f.isFb = true;
                    f.fbId = u.getString("facebookID");
                    f.title = u.getString("facebookName");
                    Ion.with(dat.context)
                            .load("http://graph.facebook.com/" + f.fbId + "/picture?type=square")
                            .write(new File(dat.context.getCacheDir().getPath() + "/" + f.fbId + ".jpg"))
                            .get();
                }
                //Get the timetable
                f.timetable = result;
                try {
                    new JSONObject(f.timetable);
                }catch (JSONException e1){
                    e = new Exception("Could not parse friends timetable. Please try again!");
                    return null;
                }
                //Save friend to memory!
                dat.addFriend(f);

            }catch (Exception e1){
                e1.printStackTrace();
                e = new Exception("Oops! Something went wrong. Check credentials and try again.");
            }
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e, "done");
        }

    }
}
