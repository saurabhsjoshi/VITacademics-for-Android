/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

package com.karthikb351.vitinfo2.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapter.NavigationDrawerAdapter;
import com.karthikb351.vitinfo2.fragment.MainFragment;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import com.karthikb351.vitinfo2.model.DrawerItemClickListener;


public class MainActivity extends AppCompatActivity{

    private String topics[];
    private DrawerLayout dl;
    private ListView lv;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lv.setOnItemClickListener(new DrawerItemClickListener());
        initializeLayouts();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    void initializeLayouts()
    {

        //final class FragClasses=Class.forName("com.karthikb351.vitinfo2.fragment.MainFragment");
        final int FragmentId[]={R.id.rvCourses,R.id.rvTimeTable,R.id.rvFriends,R.id.lvSettings};
        dl=(DrawerLayout)findViewById(R.id.drawer_layout);
        lv=(ListView)findViewById(R.id.lvDrawer);
        topics = getResources().getStringArray(R.array.title_section);
         ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(topics));
         lv.setAdapter(new NavigationDrawerAdapter(this, R.layout.drawer_menu_item, stringList));

        // When navigation drawer item is clicked
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSupportFragmentManager().beginTransaction().replace(FragmentId[position], new MainFragment(), topics[position]);
                flag = 1;
            }
        });

        if(flag==0)
         getSupportFragmentManager().beginTransaction().add(R.id.flContent,new MainFragment(),"mainFragment").commit();
    }
}
