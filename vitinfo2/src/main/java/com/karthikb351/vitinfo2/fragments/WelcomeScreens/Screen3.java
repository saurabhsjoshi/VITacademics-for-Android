package com.karthikb351.vitinfo2.fragments.WelcomeScreens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karthikb351.vitinfo2.Home;
import com.karthikb351.vitinfo2.NewUser;
import com.karthikb351.vitinfo2.ParseAPI;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.Objects.VITxApi;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.OnParseFinished;
import com.parse.ParseException;

;

/**
 * Created by saurabh on 4/27/14.
 */
public class Screen3 extends Fragment {
    private TextView txt_done;
    private Button btn_go;
    private ProgressBar prg;
    private ImageView applogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newuser_3,container, false);

        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Finalizing");

        btn_go = (Button) view.findViewById(R.id.btn_start_using);
        prg = (ProgressBar) view.findViewById(R.id.prg_indeterminate);
        applogo = (ImageView)view.findViewById(R.id.app_logo);
        txt_done = (TextView) view.findViewById(R.id.lbl_load_data);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), Home.class));
            }
        });

        final ParseAPI api_login = new ParseAPI(getActivity());
        api_login.registerUser(new OnParseFinished() {
            @Override
            public void onTaskCompleted(ParseException e) {
                if(e == null || e.getCode() == ParseException.USERNAME_TAKEN)
                {
                    api_login.loginUser(new OnParseFinished() {
                        @Override
                        public void onTaskCompleted(ParseException e) {
                            if(e != null)
                                onError(new Exception("Could not register! Please check your network and try again!"));
                        }
                    });
                }
                else{
                    e.printStackTrace();
                    onError(new Exception("Could not register! Please check your network and try again!"));
                }

            }
        });
        VITxApi api = VITxApi.getInstance(getActivity());
        api.firstUser(new VITxApi.onTaskCompleted() {
            @Override
            public void onCompleted(Object result, Exception e) {
                if(e != null){
                    onError(e);
                }else{
                    DataHandler.getInstance(getActivity()).setNewUser(false);
                    DataHandler.getInstance(getActivity()).setIsHeroku(true);
                    btn_go.setEnabled(true);
                    btn_go.setBackgroundResource(R.drawable.round_shape_green);
                    prg.setVisibility(View.GONE);
                    applogo.setVisibility(View.VISIBLE);
                    txt_done.setText("Data parsed");
                    txt_done.setTextColor(Color.parseColor("#008000"));
                }
            }
        });
        return view;
    }

    private void onError(Exception e){
        try{
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            ((NewUser) getActivity()).changeScreen(1);
        }catch (Exception e1){e1.printStackTrace();}
    }
}