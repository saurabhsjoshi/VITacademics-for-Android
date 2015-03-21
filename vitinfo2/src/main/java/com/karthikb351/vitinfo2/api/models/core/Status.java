package com.karthikb351.vitinfo2.api.models.core;

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

    public Status(){

    }

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

    public Status(String message, int code) {
        this.message = message;
        this.code = code;
    }

}
