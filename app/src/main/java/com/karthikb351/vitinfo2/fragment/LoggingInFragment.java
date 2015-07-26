package com.karthikb351.vitinfo2.fragment;


import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.MainActivity;
import com.karthikb351.vitinfo2.api.NetworkController;
import com.karthikb351.vitinfo2.api.RequestConfig;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.ResultListener;



public class LoggingInFragment extends Fragment {

    private Activity thisActivity;
    private String campus, registrationNumer, dateOfBirth, mobileNumber;
    private TextView message;
    private String messageList[];
    private int progress;

    public LoggingInFragment(){

    }

    public static LoggingInFragment newInstance(String campus, String registrationNumer, String dateOfBirth, String mobileNumber){

        LoggingInFragment fragment = new LoggingInFragment();
        fragment.campus = campus;
        fragment.registrationNumer = registrationNumer;
        fragment.dateOfBirth = dateOfBirth;
        fragment.mobileNumber = mobileNumber;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logging_in,container,false);
        messageList= (getResources().getStringArray(R.array.login_messages));
        progress=0;
        message=(TextView)view.findViewById(R.id.login_message);
        loginToServer(campus,registrationNumer,dateOfBirth,mobileNumber);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        thisActivity = activity;
    }

    private void loginToServer(String campus, String registerNumber, String dateOfBirth, String mobileNumber) {

        NetworkController networkController = NetworkController.getInstance(getActivity(), campus, registerNumber, dateOfBirth, mobileNumber);

        final ResultListener resultListener = new ResultListener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(thisActivity, MainActivity.class));
            }

            @Override
            public void onFailure(Status status) {
                try {
                    Toast.makeText(thisActivity, status.getMessage(), Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                    } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgress() {
                try {
                    thisActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(progress<6)
                                message.setText(messageList[progress++]);
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        };

        RequestConfig requestConfig = new RequestConfig(new ResultListener() {
            @Override
            public void onSuccess() {
                try {
                    ((MainApplication) thisActivity.getApplication()).getDataHolderInstance().refreshData(thisActivity, resultListener);
                } catch (NullPointerException ignore){
                }
            }

            @Override
            public void onFailure(Status status) {

                resultListener.onFailure(status);
            }

            @Override
            public void onProgress() {
                try {
                    thisActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        requestConfig.addRequest(RequestConfig.REQUEST_SYSTEM);
        requestConfig.addRequest(RequestConfig.REQUEST_REFRESH);
        requestConfig.addRequest(RequestConfig.REQUEST_GRADES);
        requestConfig.addRequest(RequestConfig.REQUEST_TOKEN);

        networkController.dispatch(requestConfig);
    }

}
