package com.karthikb351.vitinfo2.old.api.models.core;

import org.parceler.Parcel;

/**
 * Created by karthikbalakrishnan on 26/03/15.
 */

@Parcel
public class ShareToken {

    String token;

    String validity;

    String issued;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }
}
