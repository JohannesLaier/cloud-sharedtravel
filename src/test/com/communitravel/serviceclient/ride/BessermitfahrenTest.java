package com.communitravel.serviceclient.ride;

import com.communitravel.serviceclient.RideServiceClientServiceTest;
import com.communitravel.serviceclient.ride.bessermitfahren.BessermitfahrenServiceClient;

public class BessermitfahrenTest extends RideServiceClientServiceTest {
    public BessermitfahrenTest() {
        super(new BessermitfahrenServiceClient());
    }
}
