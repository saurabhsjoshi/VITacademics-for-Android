package com.karthikb351.vitinfo2.model;

/**
 * Created by gaurav on 7/6/15.
 */
public class NavigationDrawerItem {
    private boolean showNotify;
    private String title;


    public NavigationDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
