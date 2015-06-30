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

package com.karthikb351.vitinfo2.api;

import android.content.Context;
import android.os.AsyncTask;

import com.karthikb351.vitinfo2.api.utilities.Network;

public class RefreshTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private Network network;

    public RefreshTask(Context context) {
        this.context = context;
        this.network = new Network(context);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            // TODO on Refresh success
        }
        else {
            // TODO on Refresh fail
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            network.refreshAll();
        }
        catch(RuntimeException rex) {
            // TODO Error handling
            return false;
        }
        return true;
    }
}
