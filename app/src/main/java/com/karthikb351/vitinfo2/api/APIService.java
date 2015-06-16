/*
 * VITacademics
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.api;

import com.karthikb351.vitinfo2.api.response.GradesResponse;
import com.karthikb351.vitinfo2.api.response.LoginResponse;
import com.karthikb351.vitinfo2.api.response.RefreshResponse;
import com.karthikb351.vitinfo2.api.response.ShareResponse;
import com.karthikb351.vitinfo2.api.response.SystemResponse;
import com.karthikb351.vitinfo2.api.response.TokenResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Part;

public interface APIService {

    @GET("/api/v2/system")
    void system(Callback<SystemResponse> callback);

    @POST("/api/v2/{campus}/login")
    void login(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<LoginResponse> callback);

    @POST("/api/v2/{campus}/refresh")
    void refresh(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<RefreshResponse> callback);

    @POST("/api/v2/{campus}/token")
    void token(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<TokenResponse> callback);

    @POST("/api/v2/{campus}/grades")
    void grades(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, Callback<GradesResponse> callback);

    @POST("/api/v2/{campus}/share")
    void share(@Part("campus") String campus, @Field("token") String token, @Field("receiver") String receiver, Callback<ShareResponse> callback);

    @POST("/api/v2/{campus}/share")
    void share(@Part("campus") String campus, @Field("regno") String regno, @Field("dob") String dob, @Field("mobile") String mobile, @Field("receiver") String receiver, Callback<ShareResponse> callback);
}
