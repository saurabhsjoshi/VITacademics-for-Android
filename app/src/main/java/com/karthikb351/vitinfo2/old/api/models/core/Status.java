package com.karthikb351.vitinfo2.old.api.models.core;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by karthikbalakrishnan on 23/02/15.
 */
@Parcel
public class Status {

    @SerializedName("message")
    String message;

    @SerializedName("code")
    int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
