package com.communitravel.serviceclient;

import com.communitravel.model.Place;
import com.communitravel.model.Ride;
import com.communitravel.serviceclient.ride.ServiceClientRide;
import com.communitravel.serviceclient.ride.blablacar.BlablacarServiceClient;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@Ignore
public class RideServiceClientServiceTest {

    private ServiceClientRide serviceClientRide;

    public RideServiceClientServiceTest(ServiceClientRide serviceClientRide) {
        this.serviceClientRide = serviceClientRide;
    }

    @Test
    public void findRide() {

        Place departure = new Place("Berlin");
        Place arrival = new Place("Hamburg");

        Date departureDate = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 2);
        Date arrivalDate = new Date(departureDate.getDate() + 1000 * 60 * 60 * 24 * 7);

        int seats = 3;

        List<Ride> rides = serviceClientRide.findRides(departure, arrival, departureDate, arrivalDate, seats);
        for (Ride ride : rides) {
            assertEquals(departure.getName(), ride.getSource().getName());
            assertEquals(arrival.getName(), ride.getDestination().getName());
        }
    }
}
