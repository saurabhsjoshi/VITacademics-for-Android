package com.karthikb351.vitinfo2.utility;

/**
 * Created by karthikbalakrishnan on 05/07/15.
 */
public abstract class ResultListener {

    public abstract void onSuccess();

    public abstract void onFailure();

    public void onProgress() {

    }
}
