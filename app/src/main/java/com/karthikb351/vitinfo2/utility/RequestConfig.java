package com.karthikb351.vitinfo2.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthikbalakrishnan on 05/07/15.
 */
public class RequestConfig  {

    public final static int LOGIN = 0;
    public final static int REFRESH = 1;
    public final static int GRADES = 2;
    public final static int ALL = 3;

    List<Integer> requests;

    RequestConfig() {
        requests = new ArrayList<Integer>();
    }

    public void addRequest(int request) {
        requests.add(request);
    }

    public void removeRequest(int r) {
        for(int i = 0; i< requests.size(); i++) {
            if(requests.get(i)==r)
                requests.remove(i);
        }
    }

    public List<Integer> getRequests() {
        return requests;
    }

    public void setRequests(List<Integer> requests) {
        this.requests = requests;
    }
}
