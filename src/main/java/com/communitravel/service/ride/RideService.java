package com.communitravel.service.ride;

import com.communitravel.model.Ride;
import com.communitravel.model.Trip;
import com.communitravel.serviceclient.ride.ServiceClientRide;
import com.communitravel.serviceclient.ride.bessermitfahren.BessermitfahrenServiceClient;
import com.communitravel.serviceclient.ride.blablacar.BlablacarServiceClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RideService - class to find rides of all defined services
 */
@Path("/ride")
public class RideService {

    private List<ServiceClientRide> services = new ArrayList<ServiceClientRide>();

    public RideService() {
        services.add(new BlablacarServiceClient());
        services.add(new BessermitfahrenServiceClient());
    }

    /**
     * Finds rides by the specified dates and cities
     * @return List<Ride> available rides
     */
    @POST
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Ride> findRides(Trip trip) {
        List<Ride> rides = new ArrayList<Ride>();
        for (ServiceClientRide serviceClientRide : services) {
            rides.addAll(serviceClientRide.findRides(trip.getDeparture(), trip.getArrival(), trip.getDepartureDate(), trip.getArrivalDate(), trip.getSeats()));
        }
        Collections.sort(rides);
        return rides;
    }
}
