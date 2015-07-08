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

package com.karthikb351.vitinfo2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @Expose
    @SerializedName("supported")
    private String earliestSupportedVersion;

    @Expose
    @SerializedName("latest")
    private String latestVersion;

    public Client() {
    }

    public Client(String earliestSupportedVersion, String latestVersion) {
        this.earliestSupportedVersion = earliestSupportedVersion;
        this.latestVersion = latestVersion;
    }

    public String getEarliestSupportedVersion() {
        return earliestSupportedVersion;
    }

    public void setEarliestSupportedVersion(String earliestSupportedVersion) {
        this.earliestSupportedVersion = earliestSupportedVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }
}
