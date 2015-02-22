package com.karthikb351.vitinfo2.api;

import com.karthikb351.vitinfo2.api.models.LoginResponse;
import com.karthikb351.vitinfo2.api.models.RefreshResponse;
import com.karthikb351.vitinfo2.api.models.ShareTokenResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by karthikbalakrishnan on 04/02/15.
 */
public interface VITacademicsService {

    @POST("/api/v2/{campus}/login")
    void login(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, Callback<LoginResponse> callback);

    @POST("/api/v2/{campus}/refresh")
    void refresh(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, Callback<RefreshResponse> callback);

    @POST("/api/v2/{campus}/token")
    void getShareToken(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, Callback<ShareTokenResponse> callback);
}
