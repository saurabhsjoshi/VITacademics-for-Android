package com.karthikb351.vitinfo2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.karthikb351.vitinfo2.R;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by pulkit on 08/06/2015.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    Context context ;
    ArrayList<String> objects ;
    class ViewHolder
    {
        public ImageView drawerIcon ;
        public TextView drawerText ;
    }
    public NavigationDrawerAdapter(Context context , int resource , ArrayList<String> objects)
    {
        super(context,resource,objects);
        this.context=context;
        this.objects = objects ;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder ;
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.drawer_menu_item,parent,false);
            holder = new ViewHolder();
            holder.drawerIcon = (ImageView)view.findViewById(R.id.drawerIcon);
            holder.drawerText = (TextView)view.findViewById(R.id.drawerText);
            view.setTag(holder);
        }
        else
         holder = (ViewHolder)view.getTag();

        //    holder.drawerIcon.setImageResource(context.getResources().getIdentifier(objects.get(position),"drawable",context.getPackageName()));
            holder.drawerText.setText(objects.get(position));
            return view ;
    }

}
