package com.karthikb351.vitinfo2.LayoutManagers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by gaurav on 15/6/15.
 */
public class CourseLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {

        return null;
    }
    @Override
    public void scrollToPosition(int position) {

    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }
}
