/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.contributors;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.contract.Contributor;

import java.util.List;

public class ContributorListAdapter extends RecyclerView.Adapter<ContributorListAdapter.ContributorViewHolder> {

    List<Contributor> contributors;
    @Override
    public ContributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     return null;
    }

    @Override
    public void onBindViewHolder(ContributorViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }

    public class ContributorViewHolder extends RecyclerView.ViewHolder{

        public ContributorViewHolder(View view){
            super(view);

        }
    }
}