package com.communitravel.serviceclient.room;

import com.communitravel.serviceclient.room.airbnb.AirbnbServiceClient;
import com.communitravel.serviceclient.RoomServiceClientServiceTest;

public class AirbnbTest extends RoomServiceClientServiceTest {

    public AirbnbTest() {
        super(new AirbnbServiceClient());
    }
}
