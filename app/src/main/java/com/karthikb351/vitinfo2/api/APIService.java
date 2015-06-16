package com.karthikb351.vitinfo2.api;

import com.karthikb351.vitinfo2.api.response.LoginResponse;
import com.karthikb351.vitinfo2.api.response.RefreshResponse;
import com.karthikb351.vitinfo2.api.response.ShareResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by karthikbalakrishnan on 16/06/15.
 */
public interface APIService {

    @POST("/api/v2/{campus}/login")
    void login(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, Callback<LoginResponse> callback);

    @POST("/api/v2/{campus}/refresh")
    void refresh(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, Callback<RefreshResponse> callback);

    @POST("/api/v2/{campus}/token")
    void getShareToken(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, Callback<ShareResponse> callback);
}
