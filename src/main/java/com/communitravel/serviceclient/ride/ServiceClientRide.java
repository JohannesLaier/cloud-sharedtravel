package com.communitravel.serviceclient.ride;

import com.communitravel.model.Place;
import com.communitravel.model.Ride;
import com.communitravel.serviceclient.ServiceClient;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * ServiceClientRide - abstract class to request rides from an api
 */
public abstract class ServiceClientRide extends ServiceClient {

    public ServiceClientRide(String apiURL, String apiKey) {
        super(apiURL, apiKey);
    }

    public abstract List<Ride> findRides(Place departure, Place arrival, Date departureDate, Date arrivalDate, int seats);
}
