/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> objects;

    public NavigationDrawerAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.drawer_menu_item, parent, false);
            holder = new ViewHolder();
            holder.drawerIcon = (ImageView) view.findViewById(R.id.drawer_icon);
            holder.drawerText = (TextView) view.findViewById(R.id.drawer_text);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        //    holder.drawer_icon.setImageResource(context.getResources().getIdentifier(objects.get(position),"drawable",context.getPackageName()));
        holder.drawerIcon.setImageResource(R.mipmap.ic_face_black_24dp);
        holder.drawerText.setText(objects.get(position));
        return view;
    }

    class ViewHolder {
        public ImageView drawerIcon;
        public TextView drawerText;
    }

}
