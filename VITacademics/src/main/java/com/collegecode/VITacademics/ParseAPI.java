package com.collegecode.VITacademics;

import android.content.Context;
import android.os.AsyncTask;

import com.collegecode.objects.DataHandler;
import com.collegecode.objects.OnParseFinished;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

/**
 * Created by saurabh on 5/11/14.
 */
public class ParseAPI {
    private Context context;
    private DataHandler dat;

    public ParseAPI(Context context){
        this.context = context;
        dat = new DataHandler(context);
    }

    public void save_current_user(OnParseFinished listner){
        saveUser_Async reg = new saveUser_Async();
        reg.listner = listner;
        reg.execute();
    }

    public void registerUser(OnParseFinished listner){
        registerUser_Async reg = new registerUser_Async();
        reg.listner = listner;
        reg.execute();
    }

    public void loginUser(OnParseFinished listner){
        loginUser_Async reg = new loginUser_Async();
        reg.listner = listner;
        reg.execute();
    }
    public void logoutUser(OnParseFinished listner){
        logoutUser_Async reg = new logoutUser_Async();
        reg.listner = listner;
        reg.execute();
    }

    public void parseInit(){
        new parseInit_Async().execute();
    }

    private class loginUser_Async extends AsyncTask<Void,Void,Void>{
        public OnParseFinished listner;
        private ParseException e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ParseUser.logIn(dat.getRegNo(), dat.getDOBString());
                String t = ParseUser.getCurrentUser().get("isSignedIn").toString().trim();
                if(t.equals("true")){
                    dat.setFbLogin(false);
                    ParseUser user = ParseUser.getCurrentUser();
                    user.setUsername(dat.getRegNo());
                    user.setPassword(dat.getDOBString());
                    ParseFacebookUtils.unlink(ParseUser.getCurrentUser());
                    user.put("isSignedIn","false");
                    user.put("registrationNumber",dat.getRegNo());
                    user.put("platform", "Android");
                    user.put("dateOfBirth", dat.getDOBString());
                    user.save();
                }
            }catch (ParseException e){
                this.e = e;
            }
            return null;
        }
        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e);
        }
    }

    private class saveUser_Async extends AsyncTask<Void,Void,Void>{
        public OnParseFinished listner;
        private ParseException e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ParseUser.getCurrentUser().save();
            }catch (ParseException e){
                this.e = e;
            }
            return null;
        }
        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e);
        }
    }

    private class parseInit_Async extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            Parse.initialize(context, "vtpDFHGacMwZIpMtpDaFuu0ToBol9b9nQM9VD57N", "OCKn8dB6wqeGqvSdYbXHgYe9mDGFb2yukyDHT3Fs");
            ParseFacebookUtils.initialize("239533019505160");
            return null;
        }
    }

    private class registerUser_Async extends AsyncTask<Void,Void,Void> {
        public OnParseFinished listner;
        private ParseException e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            ParseUser user = new ParseUser();
            user.setUsername(dat.getRegNo());
            user.setPassword(dat.getDOBString());
            user.put("isSignedIn","false");
            user.put("registrationNumber",dat.getRegNo());
            user.put("platform", "Android");
            user.put("dateOfBirth", dat.getDOBString());
            try {
                user.signUp();
                user.save();
            } catch (ParseException e) {
                this.e = e;
            }
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e);
        }

    }

    private class logoutUser_Async extends AsyncTask<Void,Void,Void> {
        public OnParseFinished listner;
        private ParseException e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            ParseUser user = ParseUser.getCurrentUser();
            user.put("isSignedIn","false");
            try
            {
                user.save();
                ParseFacebookUtils.getSession().closeAndClearTokenInformation();
                ParseFacebookUtils.unlink(user);

            } catch (ParseException e) {
                this.e = e;
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e);
        }

    }




}
