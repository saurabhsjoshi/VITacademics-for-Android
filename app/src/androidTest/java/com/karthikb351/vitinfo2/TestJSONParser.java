/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 *
 * This file is part of VITacademics.
 * VITacademics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VITacademics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VITacademics.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.karthikb351.vitinfo2.api.StatusCodes;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.response.GradesResponse;
import com.karthikb351.vitinfo2.response.LoginResponse;
import com.karthikb351.vitinfo2.response.RefreshResponse;
import com.karthikb351.vitinfo2.response.SystemResponse;
import com.karthikb351.vitinfo2.response.TokenResponse;
import com.karthikb351.vitinfo2.utility.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestJSONParser extends AndroidTestCase {

    private Gson gson;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        gson = new Gson();
    }

    public void testSystemJSON() {
        BufferedReader jsonStreamReader = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(R.raw.api_sample_system)));
        SystemResponse systemResponse = gson.fromJson(jsonStreamReader, SystemResponse.class);

        assertNotNull(systemResponse);
        assertNotNull(systemResponse.getStatus());

        assertNotNull(systemResponse.getAndroid());
        assertNotNull(systemResponse.getContributors());
        assertNotNull(systemResponse.getMessages());
        assertNotNull(systemResponse.getAndroid().getEarliestSupportedVersion());
        assertNotNull(systemResponse.getAndroid().getLatestVersion());

        assertEquals(StatusCodes.SUCCESS, systemResponse.getStatus().getCode());
    }

    public void testLoginJSON() {
        BufferedReader jsonStreamReader = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(R.raw.api_sample_login)));
        LoginResponse loginResponse = gson.fromJson(jsonStreamReader, LoginResponse.class);

        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getStatus());

        assertNotNull(loginResponse.getCampus());
        assertNotNull(loginResponse.getRegisterNumber());
        assertNotNull(loginResponse.getDateOfBirth());
        if (loginResponse.getCampus().equals(Constants.CAMPUS_VELLORE)) {
            assertNotNull(loginResponse.getMobileNumber());
        }

        assertEquals(StatusCodes.SUCCESS, loginResponse.getStatus().getCode());
    }

    public void testRefreshJSON() {
        BufferedReader jsonStreamReader = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(R.raw.api_sample_refresh)));
        RefreshResponse refreshResponse = gson.fromJson(jsonStreamReader, RefreshResponse.class);

        assertNotNull(refreshResponse);
        assertNotNull(refreshResponse.getStatus());

        assertNotNull(refreshResponse.getCampus());
        assertNotNull(refreshResponse.getRegisterNumber());
        assertNotNull(refreshResponse.getDateOfBirth());
        if (refreshResponse.getCampus().equals(Constants.CAMPUS_VELLORE)) {
            assertNotNull(refreshResponse.getMobileNumber());
        }
        assertNotNull(refreshResponse.getSemester());
        assertNotNull(refreshResponse.getCourses());
        assertNotNull(refreshResponse.getWithdrawnCourses());
        assertNotNull(refreshResponse.getRefreshed());
        assertFalse(refreshResponse.isCached());

        assertEquals(StatusCodes.SUCCESS, refreshResponse.getStatus().getCode());
    }

    public void testGradesJSON() {
        BufferedReader jsonStreamReader = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(R.raw.api_sample_grades)));
        GradesResponse gradesResponse = gson.fromJson(jsonStreamReader, GradesResponse.class);

        assertNotNull(gradesResponse);
        assertNotNull(gradesResponse.getStatus());

        assertNotNull(gradesResponse.getCampus());
        assertNotNull(gradesResponse.getRegisterNumber());
        assertNotNull(gradesResponse.getDateOfBirth());
        if (gradesResponse.getCampus().equals(Constants.CAMPUS_VELLORE)) {
            assertNotNull(gradesResponse.getMobileNumber());
        }
        assertNotNull(gradesResponse.getCgpa());
        assertNotNull(gradesResponse.getCreditsEarned());
        assertNotNull(gradesResponse.getCreditsRegistered());
        assertNotNull(gradesResponse.getGrades());
        assertNotNull(gradesResponse.getGradeCount());
        assertNotNull(gradesResponse.getSemesterWiseGrades());
        assertNotNull(gradesResponse.getRefreshed());
        assertFalse(gradesResponse.isCached());

        assertEquals(StatusCodes.SUCCESS, gradesResponse.getStatus().getCode());
    }

    public void testTokenJSON() {
        BufferedReader jsonStreamReader = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(R.raw.api_sample_token)));
        TokenResponse tokenResponse = gson.fromJson(jsonStreamReader, TokenResponse.class);

        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getStatus());

        assertNotNull(tokenResponse.getCampus());
        assertNotNull(tokenResponse.getRegisterNumber());
        assertNotNull(tokenResponse.getDateOfBirth());
        if (tokenResponse.getCampus().equals(Constants.CAMPUS_VELLORE)) {
            assertNotNull(tokenResponse.getMobileNumber());
        }
        assertNotNull(tokenResponse.getTokenShare());

        assertEquals(StatusCodes.SUCCESS, tokenResponse.getStatus().getCode());
    }

    public void testShareJSON() {
        BufferedReader jsonStreamReader = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(R.raw.api_sample_share)));
        Friend friend = gson.fromJson(jsonStreamReader, Friend.class);

        assertNotNull(friend);
        assertNotNull(friend.getStatus());

        assertNotNull(friend.getCampus());
        assertNotNull(friend.getRegisterNumber());
        assertNotNull(friend.getDateOfBirth());
        if (friend.getCampus().equals(Constants.CAMPUS_VELLORE)) {
            assertNotNull(friend.getMobileNumber());
        }
        assertNotNull(friend.getCourses());
        assertNotNull(friend.getRefreshed());

        assertEquals(StatusCodes.SUCCESS, friend.getStatus().getCode());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
