package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;

;

/**
 * Created by saurabh on 4/22/14.
 */
public class DrawerListAdapter extends BaseAdapter{
    private int mSelectedItem = 0;
    String titles[];
    int img_resources[];
    private static LayoutInflater inflater=null;
    private Context context;

    public DrawerListAdapter(Context context, String title[], int img[]){
        this.titles = title;
        this.context = context;
        this.img_resources = img;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;

        //Check if the view is empty
        if(vi==null)
            vi = inflater.inflate(R.layout.drawer_list_item, viewGroup, false);

        TextView lbl_drawer = (TextView) vi.findViewById(R.id.lbl_drawer_title);
        ImageView img_drawer = (ImageView) vi.findViewById(R.id.img_drawer);

        if(mSelectedItem < getCount())
        {
            if(mSelectedItem == i){
                lbl_drawer.setTextColor(context.getResources().getColor(R.color.accent));
                vi.setBackgroundColor(Color.parseColor("#fff4f4f4"));
            }
            else{
                lbl_drawer.setTextColor(Color.BLACK);
                vi.setBackgroundColor(Color.TRANSPARENT);
            }


        }
        lbl_drawer.setText(titles[i]);
        img_drawer.setImageResource(img_resources[i]);

        return vi;
    }

    public void setSelectedItem(int pos){
        mSelectedItem = pos;
    }
}
