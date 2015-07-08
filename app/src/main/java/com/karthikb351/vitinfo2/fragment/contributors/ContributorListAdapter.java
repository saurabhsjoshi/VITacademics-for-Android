/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
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

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Contributor;

import java.util.ArrayList;

public class ContributorListAdapter extends RecyclerView.Adapter<ContributorListAdapter.ContributorViewHolder> {

    ArrayList<Contributor> contributors;
    Context context;

    public ContributorListAdapter(Context context, ArrayList<Contributor> contributors) {
        this.context = context;
        this.contributors = contributors;

    }

    @Override
    public ContributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.card_contributors, parent, false);
        return new ContributorViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ContributorViewHolder holder, int position) {
        holder.contributorMail.setText(contributors.get(position).getEmail());
        holder.contributorGit.setText(contributors.get(position).getGithubProfile());
        holder.contributorRole.setText(contributors.get(position).getRole());
        holder.contributorName.setText(contributors.get(position).getName());
        holder.contributorId.setText(contributors.get(position).getContributorId());
    }


    @Override
    public int getItemCount() {
        return contributors.size();
    }

    public class ContributorViewHolder extends RecyclerView.ViewHolder {

        public TextView contributorId, contributorName, contributorRole, contributorGit, contributorMail;

        public ContributorViewHolder(View view) {
            super(view);
            //contributorId=(TextView)view.findViewById(R.id.contributor_id);
            contributorName = (TextView) view.findViewById(R.id.contributor_name);
            contributorRole = (TextView) view.findViewById(R.id.contributor_role);
            contributorGit = (TextView) view.findViewById(R.id.contributor_github_profile);
            contributorMail = (TextView) view.findViewById(R.id.contributor_email);
        }
    }

}