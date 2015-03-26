package com.karthikb351.vitinfo2.bus.events;

import com.karthikb351.vitinfo2.api.models.LoginResponse;

/**
 * Created by karthikbalakrishnan on 18/03/15.
 */
public class LoginCompleteEvent {

    LoginResponse loginResponse;

    public LoginCompleteEvent(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
