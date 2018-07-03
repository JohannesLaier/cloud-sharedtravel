package com.communitravel.service.room;

import com.communitravel.model.Place;
import com.communitravel.model.Room;
import com.communitravel.model.Trip;
import com.communitravel.serviceclient.room.ServiceClientRoom;
import com.communitravel.serviceclient.room.NineFlat.NineFlatServiceClient;
import com.communitravel.serviceclient.room.airbnb.AirbnbServiceClient;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * RoomService - class to find rooms of all defined services
 */
@Path("/room")
public class RoomService extends HttpServlet {
    private List<ServiceClientRoom> services = new ArrayList<ServiceClientRoom>();

    public RoomService() {
        services.add(new AirbnbServiceClient());
        services.add(new NineFlatServiceClient());
    }

    /**
     * Finds rides by the specified dates and cities
     * @return List<Room> available rides
     */
    @POST
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Room> findRooms(Trip trip) {
        List<Room> rooms = new ArrayList<Room>();
        for (ServiceClientRoom serviceClientRoom : services) {
            rooms.addAll(serviceClientRoom.findRooms(trip.getArrival(),
                    trip.getDepartureDate(), trip.getArrivalDate(), trip.getSeats()));
        }
        Collections.sort(rooms);
        return rooms;
    }
}
