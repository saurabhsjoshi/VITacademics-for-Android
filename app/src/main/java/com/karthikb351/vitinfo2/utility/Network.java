/*
 * VITacademics
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

package com.karthikb351.vitinfo2.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.api.VITacademicsAPI;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.event.FriendEvent;
import com.karthikb351.vitinfo2.event.RefreshActivityEvent;
import com.karthikb351.vitinfo2.event.RefreshEvent;
import com.karthikb351.vitinfo2.event.SuccessEvent;

import java.util.List;

import de.greenrobot.event.EventBus;

public class Network {

    private static Network network;
    private String campus;
    private String registerNumber;
    private String dateOfBirth;
    private String mobileNumber;
    private VITacademicsAPI viTacademicsAPI;
    private int friendCount;
    private int refreshedFriends;
    private boolean refreshed;

    private Network(Context context, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        this.campus = campus;
        this.registerNumber = registerNumber;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;

        this.viTacademicsAPI = new VITacademicsAPI(context);
        network = this;

        EventBus.getDefault().register(this);
    }

    private Network(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        this.campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
        this.registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
        this.dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
        this.mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

        this.viTacademicsAPI = new VITacademicsAPI(context);
        network = this;
    }

    public static Network getNetworkSingleton(Context context) {
        if (network != null) {
            network.viTacademicsAPI = new VITacademicsAPI(context);
            return network;
        }
        return new Network(context);
    }

    public static Network getNetworkSingleton(Context context, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        if (network != null) {
            network.viTacademicsAPI = new VITacademicsAPI(context);

            network.campus = campus;
            network.registerNumber = registerNumber;
            network.dateOfBirth = dateOfBirth;
            network.mobileNumber = mobileNumber;
            return network;
        }
        return new Network(context, campus, registerNumber, dateOfBirth, mobileNumber);
    }

    public static void dispatch(RequestConfig requestConfig) {

    }


    public void getAllFriends() {
        List<Friend> friends = Friend.listAll(Friend.class);
        friendCount = friends.size();
        for (Friend friend : friends) {
            viTacademicsAPI.share(friend.getCampus(), friend.getRegisterNumber(), friend.getDateOfBirth(), friend.getMobileNumber(), this.registerNumber, new ResultListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    public void refreshAll() {
        refreshed = false;
        refreshedFriends = 0;
        SuccessEvent successEvent = new SuccessEvent(false, false, false, false, false);
        viTacademicsAPI.system(successEvent, new ResultListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        EventBus.getDefault().unregister(this);
        super.finalize();
    }

    public void onEvent(SuccessEvent successEvent) {
        if (successEvent.isSystemDone()) {
            if (successEvent.isLoginRequired()) {
                viTacademicsAPI.login(campus, registerNumber, dateOfBirth, mobileNumber, successEvent, new ResultListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            } else {
                if (successEvent.isRefreshDone() && successEvent.isGradesDone()) {
                    if (successEvent.isTokenDone()) {
                        refreshed = true;
                        EventBus.getDefault().post(new RefreshEvent());
                    } else {
                        viTacademicsAPI.token(campus, registerNumber, dateOfBirth, mobileNumber, successEvent, new ResultListener() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                } else {
                    if (successEvent.isRefreshDone()) {
                        viTacademicsAPI.grades(campus, registerNumber, dateOfBirth, mobileNumber, successEvent, new ResultListener() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    } else {
                        viTacademicsAPI.refresh(campus, registerNumber, dateOfBirth, mobileNumber, successEvent, new ResultListener() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                }
            }
            getAllFriends();
        } else {
            viTacademicsAPI.system(successEvent, new ResultListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    public void onEvent(FriendEvent friendEvent) {
        refreshedFriends = refreshedFriends + 1;
        EventBus.getDefault().post(new RefreshEvent());
    }

    public void onEvent(RefreshEvent refreshEvent) {
        if (friendCount == refreshedFriends && refreshed) {
            EventBus.getDefault().post(new RefreshActivityEvent());
        }
    }


}
