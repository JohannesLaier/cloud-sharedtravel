package com.communitravel.serviceclient.ride;

import com.communitravel.serviceclient.RideServiceClientServiceTest;
import com.communitravel.serviceclient.ride.blablacar.BlablacarServiceClient;

public class BlablaCarTest extends RideServiceClientServiceTest {
    public BlablaCarTest() {
        super(new BlablacarServiceClient());
    }
}
