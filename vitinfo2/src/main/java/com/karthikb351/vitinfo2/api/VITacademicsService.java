package com.karthikb351.vitinfo2.api;

import com.karthikb351.vitinfo2.api.models.LoginResponse;
import com.karthikb351.vitinfo2.api.models.RefreshResponse;
import com.karthikb351.vitinfo2.api.models.ShareTokenResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by karthikbalakrishnan on 04/02/15.
 */
public interface VITacademicsService {

    @FormUrlEncoded
    @POST("/api/v2/{campus}/login")
    void login(@Path("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<LoginResponse> callback);

    @FormUrlEncoded
    @POST("/api/v2/{campus}/refresh")
    void refresh(@Path("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<RefreshResponse> callback);

    @FormUrlEncoded
    @POST("/api/v2/{campus}/token")
    void getShareToken(@Path("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<ShareTokenResponse> callback);
}
