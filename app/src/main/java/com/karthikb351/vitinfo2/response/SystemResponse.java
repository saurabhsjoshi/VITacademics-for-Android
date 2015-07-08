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

package com.karthikb351.vitinfo2.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.karthikb351.vitinfo2.contract.Contributor;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.model.Client;
import com.karthikb351.vitinfo2.model.Status;

public class SystemResponse {

    @Expose
    @SerializedName("android")
    private Client android;

    @Expose
    @SerializedName("ios")
    private Client ios;

    @Expose
    @SerializedName("windows")
    private Client windows;

    @Expose
    @SerializedName("messages")
    private Message[] messages;

    @Expose
    @SerializedName("contributors")
    private Contributor[] contributors;

    @Expose
    @SerializedName("status")
    private Status status;

    public SystemResponse() {
    }

    public SystemResponse(Client android, Client ios, Client windows, Message[] messages, Contributor[] contributors, Status status) {
        this.android = android;
        this.ios = ios;
        this.windows = windows;
        this.messages = messages;
        this.contributors = contributors;
        this.status = status;
    }

    public Client getAndroid() {
        return android;
    }

    public void setAndroid(Client android) {
        this.android = android;
    }

    public Client getIos() {
        return ios;
    }

    public void setIos(Client ios) {
        this.ios = ios;
    }

    public Client getWindows() {
        return windows;
    }

    public void setWindows(Client windows) {
        this.windows = windows;
    }

    public Message[] getMessages() {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Contributor[] getContributors() {
        return contributors;
    }

    public void setContributors(Contributor[] contributors) {
        this.contributors = contributors;
    }
}
