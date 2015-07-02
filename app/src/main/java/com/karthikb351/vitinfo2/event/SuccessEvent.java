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

package com.karthikb351.vitinfo2.event;

public class SuccessEvent {

    private boolean systemDone;

    private boolean loginRequired;

    private boolean refreshDone;

    private boolean gradesDone;

    private boolean tokenDone;

    public SuccessEvent(boolean systemDone, boolean loginRequired, boolean refreshDone, boolean gradesDone, boolean tokenDone) {
        this.systemDone = systemDone;
        this.loginRequired = loginRequired;
        this.refreshDone = refreshDone;
        this.gradesDone = gradesDone;
        this.tokenDone = tokenDone;
    }

    public boolean isSystemDone() {
        return systemDone;
    }

    public void setSystemDone(boolean systemDone) {
        this.systemDone = systemDone;
    }

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public boolean isRefreshDone() {
        return refreshDone;
    }

    public void setRefreshDone(boolean refreshDone) {
        this.refreshDone = refreshDone;
    }

    public boolean isGradesDone() {
        return gradesDone;
    }

    public void setGradesDone(boolean gradesDone) {
        this.gradesDone = gradesDone;
    }

    public boolean isTokenDone() {
        return tokenDone;
    }

    public void setTokenDone(boolean tokenDone) {
        this.tokenDone = tokenDone;
    }
}
