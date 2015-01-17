package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;

/**
 * Created by saurabh on 2015-01-17.
 */
public class AddFriendResponse {
    @Expose
    private Status status;

    @Expose
    private Data data;

    public Status getStatus(){
        return status;
    }

    public Data getData(){
        return data;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void setData(Data data){
        this.data = data;
    }
}
