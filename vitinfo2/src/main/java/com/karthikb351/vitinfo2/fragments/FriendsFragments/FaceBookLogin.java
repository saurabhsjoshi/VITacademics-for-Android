package com.karthikb351.vitinfo2.fragments.FriendsFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.karthikb351.vitinfo2.Home;
import com.karthikb351.vitinfo2.ParseAPI;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.OnParseFinished;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

;

/**
 * Created by saurabh on 5/11/14.
 */
public class FaceBookLogin extends Fragment{
    ImageButton btn_login;
    public ProgressDialog pdia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_facebook_login,container, false);
        btn_login = (ImageButton) v.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdia = new ProgressDialog(getActivity());
                pdia.setMessage("Loading");
                pdia.setCancelable(true);
                pdia.show();
                loginwithFaceBook();
            }
        });

        return v;
    }

    ParseUser parse_user;
    public void loginwithFaceBook(){
        parse_user = ParseUser.getCurrentUser();
        if (!ParseFacebookUtils.isLinked(parse_user)) {
            ParseFacebookUtils.link(parse_user, getActivity(), new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if(ex != null)
                        ex.printStackTrace();
                    if (ParseFacebookUtils.isLinked(parse_user)) {
                        Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                if (user != null) {
                                    Log.i("VITacademics PARSE API", response.toString());
                                    parse_user.put("facebookID", user.getId());
                                    parse_user.put("facebookName", user.getName());
                                    parse_user.put("isSignedIn", "true");
                                    ParseAPI p = new ParseAPI(getActivity());
                                    p.save_current_user(new OnParseFinished() {
                                        @Override
                                        public void onTaskCompleted(ParseException e) {
                                            if (e == null) {
                                                new DataHandler(getActivity()).setFbLogin(true);
                                                Toast.makeText(getActivity(), "Signed in!", Toast.LENGTH_SHORT).show();
                                                ((Home) getActivity()).selectItem_Async(3);
                                                Log.i("VITacademics PARSE API", "Done");
                                            } else
                                                e.printStackTrace();
                                            pdia.dismiss();
                                        }

                                    });
                                }
                            }
                        });
                    }
                    else{
                        ((Home) getActivity()).selectItem_Async(3); pdia.dismiss();
                        Toast.makeText(getActivity(), "Error Occured! Please try again." , Toast.LENGTH_SHORT).show();
                    }

                }
             });
        }
        else{((Home) getActivity()).selectItem_Async(3); pdia.dismiss();new DataHandler(getActivity()).setFbLogin(true);
            Toast.makeText(getActivity(), "Signed in!" , Toast.LENGTH_SHORT).show();}
    }



    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_fb_login, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_fb_cancel:
                ((Home) getActivity()).selectItem_Async(3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
