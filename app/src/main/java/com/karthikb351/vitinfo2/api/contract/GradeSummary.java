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

package com.karthikb351.vitinfo2.api.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GradeSummary {

    @Expose
    @SerializedName("s")
    private int sCount;

    @Expose
    @SerializedName("a")
    private int aCount;

    @Expose
    @SerializedName("b")
    private int bCount;

    @Expose
    @SerializedName("c")
    private int cCount;

    @Expose
    @SerializedName("d")
    private int dCount;

    @Expose
    @SerializedName("e")
    private int eCount;

    @Expose
    @SerializedName("f")
    private int fCount;

    @Expose
    @SerializedName("n")
    private int nCount;

    public GradeSummary() {
    }

    public GradeSummary(int sCount, int aCount, int bCount, int cCount, int dCount, int eCount, int fCount, int nCount) {
        this.sCount = sCount;
        this.aCount = aCount;
        this.bCount = bCount;
        this.cCount = cCount;
        this.dCount = dCount;
        this.eCount = eCount;
        this.fCount = fCount;
        this.nCount = nCount;
    }

    public int getsCount() {
        return sCount;
    }

    public void setsCount(int sCount) {
        this.sCount = sCount;
    }

    public int getaCount() {
        return aCount;
    }

    public void setaCount(int aCount) {
        this.aCount = aCount;
    }

    public int getbCount() {
        return bCount;
    }

    public void setbCount(int bCount) {
        this.bCount = bCount;
    }

    public int getcCount() {
        return cCount;
    }

    public void setcCount(int cCount) {
        this.cCount = cCount;
    }

    public int getdCount() {
        return dCount;
    }

    public void setdCount(int dCount) {
        this.dCount = dCount;
    }

    public int geteCount() {
        return eCount;
    }

    public void seteCount(int eCount) {
        this.eCount = eCount;
    }

    public int getfCount() {
        return fCount;
    }

    public void setfCount(int fCount) {
        this.fCount = fCount;
    }

    public int getnCount() {
        return nCount;
    }

    public void setnCount(int nCount) {
        this.nCount = nCount;
    }
}
