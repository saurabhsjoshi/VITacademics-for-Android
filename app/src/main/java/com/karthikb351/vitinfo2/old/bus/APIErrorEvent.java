package com.karthikb351.vitinfo2.old.bus;

/**
 * Created by karthikbalakrishnan on 22/02/15.
 */
public class APIErrorEvent {

    String message;

    public APIErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
