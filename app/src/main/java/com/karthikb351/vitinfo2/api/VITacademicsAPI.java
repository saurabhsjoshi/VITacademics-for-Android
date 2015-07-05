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

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.event.MessageEvent;
import com.karthikb351.vitinfo2.event.SuccessEvent;
import com.karthikb351.vitinfo2.response.GradesResponse;
import com.karthikb351.vitinfo2.response.LoginResponse;
import com.karthikb351.vitinfo2.response.RefreshResponse;
import com.karthikb351.vitinfo2.response.SystemResponse;
import com.karthikb351.vitinfo2.response.TokenResponse;
import com.karthikb351.vitinfo2.utility.Database;
import com.karthikb351.vitinfo2.utility.ResultListener;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class VITacademicsAPI {

    private Context context;
    private Database database;
    private APIService service;

    public VITacademicsAPI(Context context) {

        this.context = context;
        this.database = Database.getDatabaseSingleton(context);

        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.API_BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        this.service = restAdapter.create(APIService.class);
    }

    public APIService getAPIService() {
        return this.service;
    }

    public void system(final ResultListener resultListener) {
        service.system(new Callback<SystemResponse>() {
            @Override
            public void success(SystemResponse systemResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void system(final SuccessEvent successEvent, final ResultListener resultListener) {
        service.system(new Callback<SystemResponse>() {
            @Override
            public void success(SystemResponse systemResponse, Response response) {
                switch (systemResponse.getStatus().getCode()) {
                    case StatusCodes.SUCCESS:
                        database.saveSystem(systemResponse, new ResultListener() {
                            @Override
                            public void onSuccess() {
                                resultListener.onSuccess();
                            }

                            @Override
                            public void onFailure() {
                                resultListener.onFailure();
                            }
                        });
                        break;
                    default:
                        EventBus.getDefault().post(new MessageEvent(systemResponse.getStatus().getMessage()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(context.getString(R.string.api_system_fail));
                resultListener.onFailure();
            }
        });
    }

    public void refresh(final String campus, final String regno, final String dob, final String mobile, final SuccessEvent successEvent, final ResultListener resultListener) {
        service.refresh(campus, regno, dob, mobile, new Callback<RefreshResponse>() {
            @Override
            public void success(RefreshResponse refreshResponse, Response response) {
                switch (refreshResponse.getStatus().getCode()) {
                    case StatusCodes.SUCCESS:
                        database.saveCourses(refreshResponse, new ResultListener() {
                            @Override
                            public void onSuccess() {
                                resultListener.onSuccess();
                            }

                            @Override
                            public void onFailure() {
                                resultListener.onFailure();
                            }
                        });
                        break;
                    case StatusCodes.TIMED_OUT:
                        successEvent.setLoginRequired(true);
                        login(campus, regno, dob, mobile, successEvent, resultListener);
                        break;
                    default:
                        EventBus.getDefault().post(new MessageEvent(refreshResponse.getStatus().getMessage()));
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(context.getString(R.string.api_system_fail));
                resultListener.onFailure();
            }
        });
    }

    public void login(final String campus, final String regno, final String dob, final String mobile, final SuccessEvent successEvent, final ResultListener resultListener) {
        service.login(campus, regno, dob, mobile, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                switch (loginResponse.getStatus().getCode()) {
                    case StatusCodes.SUCCESS:
                        database.saveLogin(loginResponse, new ResultListener() {
                            @Override
                            public void onSuccess() {
                                resultListener.onSuccess();
                            }

                            @Override
                            public void onFailure() {
                                resultListener.onFailure();
                            }
                        });
                        break;
                    default:
                        EventBus.getDefault().post(new MessageEvent(loginResponse.getStatus().getMessage()));
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(context.getString(R.string.api_system_fail));
                resultListener.onFailure();
            }
        });

    }

    public void token(final String campus, final String regno, final String dob, final String mobile, final SuccessEvent successEvent, final ResultListener resultListener) {
        service.token(campus, regno, dob, mobile, new Callback<TokenResponse>() {
            @Override
            public void success(TokenResponse tokenResponse, Response response) {
                switch (tokenResponse.getStatus().getCode()) {
                    case StatusCodes.SUCCESS:
                        database.saveToken(tokenResponse, new ResultListener() {
                            @Override
                            public void onSuccess() {
                                resultListener.onSuccess();
                            }

                            @Override
                            public void onFailure() {
                                resultListener.onFailure();
                            }
                        });
                        break;
                    default:
                        EventBus.getDefault().post(new MessageEvent(tokenResponse.getStatus().getMessage()));
                        break;
                }
                resultListener.onSuccess();
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(context.getString(R.string.api_system_fail));
                resultListener.onFailure();
            }
        });
    }

    public void grades(final String campus, final String regno, final String dob, final String mobile, final SuccessEvent successEvent, final ResultListener resultListener) {
        service.grades(campus, regno, dob, mobile, new Callback<GradesResponse>() {
            @Override
            public void success(GradesResponse gradesResponse, Response response) {
                switch (gradesResponse.getStatus().getCode()) {
                    case StatusCodes.SUCCESS:
                        database.saveGrades(gradesResponse, new ResultListener() {
                            @Override
                            public void onSuccess() {
                                resultListener.onSuccess();
                            }

                            @Override
                            public void onFailure() {
                                resultListener.onFailure();
                            }
                        });
                        break;
                    case StatusCodes.TIMED_OUT:
                        successEvent.setLoginRequired(true);
                        login(campus, regno, dob, mobile, successEvent, resultListener);
                        break;
                    default:
                        EventBus.getDefault().post(new MessageEvent(gradesResponse.getStatus().getMessage()));
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(context.getString(R.string.api_system_fail));
                resultListener.onFailure();
            }
        });
    }

    public void share(final String campus, final String token, final String receiver, final ResultListener resultListener) {
        service.share(campus, token, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
                switch (friend.getStatus().getCode()) {
                    case StatusCodes.SUCCESS:
                        database.saveFriend(friend, new ResultListener() {
                            @Override
                            public void onSuccess() {
                                resultListener.onSuccess();
                            }

                            @Override
                            public void onFailure() {
                                resultListener.onFailure();
                            }
                        });
                        break;
                    default:
                        EventBus.getDefault().post(new MessageEvent(friend.getStatus().getMessage()));
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(context.getString(R.string.api_system_fail));
                resultListener.onFailure();
            }
        });
    }

    public void share(final String campus, final String regno, final String dob, final String mobile, final String receiver, final ResultListener resultListener) {
        service.share(campus, regno, dob, mobile, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
                switch (friend.getStatus().getCode()) {
                    case StatusCodes.SUCCESS:
                        database.saveFriend(friend, new ResultListener() {
                        @Override
                        public void onSuccess() {
                            resultListener.onSuccess();
                        }

                        @Override
                        public void onFailure() {
                            resultListener.onFailure();
                        }
                    });
                        break;
                    default:
                        EventBus.getDefault().post(new MessageEvent(friend.getStatus().getMessage()));
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(context.getString(R.string.api_system_fail));
                resultListener.onFailure();
            }
        });
    }
}
