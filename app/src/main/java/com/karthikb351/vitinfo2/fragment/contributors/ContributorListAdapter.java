/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 * Copyright (C) 2015  Darshan Mehta <darshanmehta17@gmail.com>
 *
 * This file is part of VITacademics.
 *
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
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import java.util.List;

public class ContributorListAdapter extends RecyclerView.Adapter<ContributorListAdapter.ContributorViewHolder> {

    private Context context;
    private List<Contributor> contributors;
    private RecyclerViewOnClickListener<Contributor> onClickListener;

    public ContributorListAdapter(Context context, List<Contributor> contributors) {
        this.context = context;
        this.contributors = contributors;
    }

    @Override
    public ContributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_contributor, parent, false);
        return new ContributorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContributorViewHolder holder, int position) {
        holder.contributorName.setText(contributors.get(position).getName());
        holder.contributorRole.setText(contributors.get(position).getRole());
    }

    public void setOnClickListener(RecyclerViewOnClickListener<Contributor> onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }

    public class ContributorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView contributorName, contributorRole;

        public ContributorViewHolder(View view) {
            super(view);

            contributorName = (TextView) view.findViewById(R.id.contributor_name);
            contributorRole = (TextView) view.findViewById(R.id.contributor_role);

            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Contributor contributor = contributors.get(getAdapterPosition());
            onClickListener.onItemClick(contributor);
        }
    }
}
