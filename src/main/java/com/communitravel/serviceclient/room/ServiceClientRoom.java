package com.communitravel.serviceclient.room;

import com.communitravel.model.Place;
import com.communitravel.model.Room;
import com.communitravel.serviceclient.ServiceClient;

import java.util.Date;
import java.util.List;

/**
 * ServiceClientRoom - abstract class to request rooms from an api
 */
public abstract class ServiceClientRoom extends ServiceClient {
    public ServiceClientRoom(String apiURL, String apiKey) {
        super(apiURL, apiKey);
    }

    public abstract List<Room> findRooms(Place place, Date departureDate, Date arrivalDate, int guests);
}
