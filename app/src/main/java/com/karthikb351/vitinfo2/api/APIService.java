package com.karthikb351.vitinfo2.api;

import com.karthikb351.vitinfo2.api.response.GradesResponse;
import com.karthikb351.vitinfo2.api.response.LoginResponse;
import com.karthikb351.vitinfo2.api.response.RefreshResponse;
import com.karthikb351.vitinfo2.api.response.ShareResponse;
import com.karthikb351.vitinfo2.api.response.SystemResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by karthikbalakrishnan on 16/06/15.
 */
public interface APIService {

    @GET("/api/v2/system")
    void system(Callback<SystemResponse> callback);

    @POST("/api/v2/{campus}/login")
    void login(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<LoginResponse> callback);

    @POST("/api/v2/{campus}/refresh")
    void refresh(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<RefreshResponse> callback);

    @POST("/api/v2/{campus}/token")
    void share(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<ShareResponse> callback);

    @POST("/api/v2/{campus}/grades")
    void grades(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<GradesResponse> callback);

}
