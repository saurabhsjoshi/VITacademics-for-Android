package com.karthikb351.vitinfo2.model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gaurav on 15/6/15.
 */
public class FriendModel {
    public String FriendName, FriendRegNo;
    public int FriendPhotoId;
    FriendModel(String FriendName, String FriendRegNo, int FriendPhotoId){
        this.FriendName=FriendName;
        this.FriendRegNo=FriendRegNo;
        this.FriendPhotoId=FriendPhotoId;
    }
}
