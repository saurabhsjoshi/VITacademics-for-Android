package com.karthikb351.vitinfo2.api.Objects;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.karthikb351.vitinfo2.api.HomeCall;
import com.karthikb351.vitinfo2.objects.DataHandler;

/**
 * Created by saurabh on 14-12-09.
 */
public class VITxApi {
    private static VITxApi mInstance;

    private Context context;

    private String regno;
    private String dob;
    private String campus;

    public interface onTaskCompleted{ public void onCompleted(Object result, Exception e); }

    public VITxApi(Context context){
        this.context = context;
        refreshCredentials();
    }

    public static VITxApi getInstance(Context context){
        if(mInstance == null)
            mInstance = new VITxApi(context);
        return mInstance;
    }

    public void loginUser(final onTaskCompleted listener){
        refreshCredentials();
        class bgTask extends AsyncTask<Void, Void, Object>{
            private Exception e;

            @Override
            protected Object doInBackground(Void... params) {
                try{
                    if(!isConnected())
                        throw new Exception("No network connection!");
                    Response temp = HomeCall.sendRequest(regno, dob, campus, "/login/auto");
                    if(temp.getStatus().getCode() == 0)
                        return temp;
                    else
                        throw new Exception(temp.getStatus().getMessage());
                }catch (Exception e){
                    this.e = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onCompleted(o, e);
            }
        }
        new bgTask().execute();
    }

    public void firstUser(final onTaskCompleted listener){
        refreshCredentials();
        class bgTask extends AsyncTask<Void, Void, Object>{
            Exception e;

            @Override
            protected Object doInBackground(Void... params) {
                try{
                    if(!isConnected())
                        throw new Exception("No network connection!");
                    Response temp = HomeCall.sendRequest(regno, dob, campus, "/data/first");
                    if(temp.getStatus().getCode() == 0){
                        DataHandler.getInstance(context).saveFirstJSON(HomeCall.json_response);
                        DataHandler.getInstance(context).saveRefreshJSON(HomeCall.json_response);
                        return temp;
                    }
                    else
                        throw new Exception(temp.getStatus().getMessage());
                }catch (Exception e){
                    this.e = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onCompleted(o, e);
            }
        }

        loginUser(new onTaskCompleted() {
            @Override
            public void onCompleted(Object result, Exception e) {
                if(e == null)
                    new bgTask().execute();
                else
                    listener.onCompleted(null, e);
            }
        });
    }

    public void refreshData(final onTaskCompleted listener){
        refreshCredentials();
        class bgTask extends AsyncTask<Void, Void, Object>{
            Exception e;

            @Override
            protected Object doInBackground(Void... params) {
                try{
                    if(!isConnected())
                        throw new Exception("No network connection!");
                    Response temp = HomeCall.sendRequest(regno, dob, campus, "/data/refresh");
                    if(temp.getStatus().getCode() == 0){
                        DataHandler.getInstance(context).saveRefreshJSON(HomeCall.json_response);
                        return temp;
                    }
                    else
                        throw new Exception(temp.getStatus().getMessage());
                }catch (Exception e){
                    this.e = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onCompleted(o, e);
            }
        }

        loginUser(new onTaskCompleted() {
            @Override
            public void onCompleted(Object result, Exception e) {
                if(e == null)
                    new bgTask().execute();
                else
                    listener.onCompleted(null, e);
            }
        });
    }

    private void refreshCredentials(){
        regno = DataHandler.getInstance(context).getRegNo();
        dob = DataHandler.getInstance(context).getDOBString();
        if(DataHandler.getInstance(context).isVellore())
            campus = "vellore";
        else
            campus = "chennai";
    }

    public boolean isConnected(){
        ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
