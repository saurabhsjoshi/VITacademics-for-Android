package com.collegecode.fragments.DetailsScreens;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Subject;

/**
 * Created by saurabh on 4/30/14.
 */
public class AttendanceDetailsFragment extends Fragment {
    Context context;
    DataHandler dat;
    Subject subject;

    Button bunk_add,bunk_sub, makeup_add, makeup_sub;
    TextView tv_title,tv_slot,tv_type,tv_code,tv_atten,tv_net_per,bunk_val,makeup_val;
    ProgressBar progBar;

    int per, max, atten, globe_makeup = 0, globe_bunk=0, t_atten, t_max,  class_offset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attendance_details,container, false);

        tv_title=(TextView)v.findViewById(R.id.title_detailed);
        tv_slot=(TextView)v.findViewById(R.id.slot_detailed);
        tv_code=(TextView)v.findViewById(R.id.code_detailed);
        tv_type=(TextView)v.findViewById(R.id.type_detailed);
        tv_atten=(TextView)v.findViewById(R.id.atten_detailed);
        tv_net_per=(TextView)v.findViewById(R.id.net_per);
        bunk_val=(TextView)v.findViewById(R.id.bunk_val);
        makeup_val=(TextView)v.findViewById(R.id.makeup_val);
        progBar=(ProgressBar)v.findViewById(R.id.progressBar_detailed);
        bunk_add=(Button)v.findViewById(R.id.bunk_add);
        bunk_sub=(Button)v.findViewById(R.id.bunk_sub);
        makeup_add=(Button)v.findViewById(R.id.makeup_add);
        makeup_sub=(Button)v.findViewById(R.id.makeup_sub);

        bunk_add.setOnClickListener(ocl);
        bunk_sub.setOnClickListener(ocl);
        makeup_add.setOnClickListener(ocl);
        makeup_sub.setOnClickListener(ocl);

        load();

       // atten_popup=(RelativeLayout)v.findViewById(R.id.atten_details_button);

        return v;
    }

    void Per(){
        per = (int) DataHandler.getPer(t_atten, t_max);
        if (DataHandler.getPer(t_atten, t_max) > per)
            per += 1;
    }

    void load(){
        tv_title.setText(subject.title);
        tv_slot.setText(subject.slot);
        tv_code.setText(subject.code);
        tv_type.setText(subject.type);
        atten = subject.attended;
        max = subject.conducted;

        bunk_val.setText("If you miss 0 more class(s)");
        makeup_val.setText("If you attend 0 more class(s)");

        class_offset = 1;

        if(subject.type.contains("Lab"))
            for( int i=0; i<subject.slot.length(); i++ ) {
                if( subject.slot.charAt(i) == '+' ) {
                    class_offset++;
                }
            }
        updateScreen();
    }

    public void updateScreen(){

        t_atten = atten+globe_makeup;
        t_max = max+globe_makeup+globe_bunk;

        Per();

        tv_net_per.setText(String.valueOf(per)+"%");
        tv_net_per.setTextColor(DataHandler.getPerColor(per));

        float x[]={5,5,5,5,5,5,5,5};
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(x, null,null));
        pgDrawable.getPaint().setColor(DataHandler.getPerColor(per));
        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progBar.setProgressDrawable(progress);
        progBar.setMax(t_max);
        progBar.setProgress(0);
        progBar.invalidate();
        progBar.setProgress(t_atten);

        if(globe_makeup==0 && globe_bunk==0)
        {
            tv_atten.setText("You have attended "+t_atten+" out of "+t_max+" classes");
        }
        else
        {
            tv_atten.setText("You would have attended "+t_atten+" out of "+t_max+" classes");
        }
    }

    OnClickListener ocl = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bunk_add:
                    onBunk(true);
                    break;

                case R.id.bunk_sub:
                    if(globe_bunk!=0)
                        onBunk(false);
                    break;

                case R.id.makeup_add:
                    onMakeup(true);
                    break;

                case R.id.makeup_sub:
                    if(globe_makeup!=0)
                        onMakeup(false);
                    break;
            }
        }
    };

    void onBunk(boolean f)
    {
        if(f)
            globe_bunk+=class_offset;
        else
            globe_bunk-=class_offset;
        if(globe_bunk<0)
        {
            globe_bunk=0;
        }

        if(globe_bunk==0)
            bunk_sub.setClickable(false);
        else
            bunk_sub.setClickable(true);
        bunk_val.setText("If you miss "+globe_bunk+" more class(s)");

        updateScreen();
    }

    void onMakeup(boolean f)
    {
        if(f)
            globe_makeup+=class_offset;
        else
            globe_makeup-=class_offset;

        if(globe_makeup<0)
            globe_makeup=0;

        if(globe_makeup==0)
            makeup_sub.setClickable(false);
        else
            makeup_sub.setClickable(true);
        makeup_val.setText("If you attend "+globe_makeup+" more class(s)");
        updateScreen();
    }

    public AttendanceDetailsFragment(Context context, Subject sub){
        this.context = context;
        this.subject = sub;

        dat = new DataHandler(context);
    }
}
