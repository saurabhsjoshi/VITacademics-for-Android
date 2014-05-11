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

    private class loginUser_Async extends AsyncTask<Void,Void,Void>{
        public OnParseFinished listner;
        private ParseException e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Parse.initialize(context, "pslFDPvG2NmCKEW3v20X9QtgOabxtAvsetd3Keq6", "o3g05te1eRhgbc7pUC6bbzFtVypUuHLEauM3x4vY");
                ParseFacebookUtils.initialize("239533019505160");
               ParseUser.logIn(dat.getRegNo(), dat.getDOBString());
            }catch (ParseException e){
                this.e = e;
            }
            return null;
        }
        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e);
        }
    }


    private class registerUser_Async extends AsyncTask<Void,Void,Void> {
        public OnParseFinished listner;
        private ParseException e = null;

        @Override
        protected Void doInBackground(Void... voids) {
            Parse.initialize(context, "pslFDPvG2NmCKEW3v20X9QtgOabxtAvsetd3Keq6", "o3g05te1eRhgbc7pUC6bbzFtVypUuHLEauM3x4vY");
            ParseFacebookUtils.initialize("239533019505160");
            ParseUser user = new ParseUser();
            user.setUsername(dat.getRegNo());
            user.setPassword(dat.getDOBString());
            try {
                user.signUp();
            } catch (ParseException e) {
                this.e = e;
            }
            return null;
        }

        protected void onPostExecute(Void voids){
            listner.onTaskCompleted(e);
        }

    }


}
