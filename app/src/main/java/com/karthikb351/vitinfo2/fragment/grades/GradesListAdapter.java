package com.karthikb351.vitinfo2.fragment.grades;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gaurav on 28/6/15.
 */
public class GradesListAdapter extends RecyclerView.Adapter<GradesListAdapter.GradesViewHolder> {

    @Override
    public GradesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(GradesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GradesViewHolder extends RecyclerView.ViewHolder{
        public GradesViewHolder(View itemView) {
            super(itemView);
        }
    }
}
