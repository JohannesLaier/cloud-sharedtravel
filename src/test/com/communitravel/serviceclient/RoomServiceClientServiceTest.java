package com.communitravel.serviceclient;

import com.communitravel.model.Place;
import com.communitravel.model.Room;
import com.communitravel.serviceclient.room.ServiceClientRoom;
import com.communitravel.serviceclient.room.airbnb.AirbnbServiceClient;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class RoomServiceClientServiceTest {
    private ServiceClientRoom serviceClientRoom;

    public RoomServiceClientServiceTest() {
        this(new AirbnbServiceClient());
    }

    public RoomServiceClientServiceTest(ServiceClientRoom serviceClientRoom) {
        this.serviceClientRoom = serviceClientRoom;
    }

    @Test
    public void findRoom() {
        Place place = new Place("Stuttgart");
        Date departureDate = new Date();
        Date arrivalDate = new Date(departureDate.getTime() + 1000 * 60 * 60 * 24 * 7);
        int guests = 1;
        List<Room> rooms = serviceClientRoom.findRooms(place, departureDate, arrivalDate, guests);
        for (Room room : rooms) {
            assertEquals(place.getName(), room.getPlace().getName());
        }
    }
}
