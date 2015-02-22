package com.karthikb351.vitinfo2.bus;

import com.squareup.otto.Bus;

/**
 * Created by karthikbalakrishnan on 05/02/15.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
