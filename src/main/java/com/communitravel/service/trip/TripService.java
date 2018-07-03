package com.communitravel.service.trip;

import com.communitravel.model.*;
import com.communitravel.service.ride.RideService;
import com.communitravel.service.room.RoomService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/trip")
public class TripService {

    private List<Place> europeCities = new ArrayList<Place>();

    public TripService() {
        europeCities.add(new Place("Amsterdam"));
        europeCities.add(new Place("Zürich"));
        europeCities.add(new Place("Wien"));
        europeCities.add(new Place("Brügge"));
        europeCities.add(new Place("Paris"));
        europeCities.add(new Place("Prag"));
        europeCities.add(new Place("Berlin"));
        europeCities.add(new Place("Hamburg"));
        europeCities.add(new Place("München"));
        europeCities.add(new Place("Brüssel"));
    }

    /**
     * Finds the cheapest trip with the cheapest room and rides
     * @return Trip cheapest trip
     */
    private Trip findCheapTrip(Trip trip) {
        List<Room> rooms = new RoomService().findRooms(trip);
        List<Ride> ridesArrival = new RideService().findRides(trip);
        List<Ride> ridesDeparture = new RideService().findRides(trip);

        Collections.sort(rooms);
        Collections.sort(ridesArrival);
        Collections.sort(ridesDeparture);

        if (ridesArrival.size() <= 0
            || ridesDeparture.size() <= 0
            || rooms.size() <= 0
        ) {
            return null;
        }

        Trip newTrip = new Trip(trip.getDeparture(), trip.getArrival());
        newTrip.setArrivalRide(ridesArrival.get(0));
        newTrip.setDepartureRide(ridesDeparture.get(0));
        newTrip.setRoom(rooms.get(0));
        return newTrip;
    }

    /**
     * Finds trips by the specified dates and cities
     * @return List<Trip> available trips
     */
    @POST
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TripMerge findTrip(Trip trip) {
        List<Room> rooms = new RoomService().findRooms(trip);
        List<Ride> ridesArrival = new RideService().findRides(trip);
        List<Ride> ridesDeparture = new RideService().findRides(trip);

        return new TripMerge(ridesDeparture, ridesArrival, rooms);
    }

    /**
     * Finds the cheapest trips to all cities defined in europeCities
     * @return List<Trip> available trips
     */
    @POST
    @Path("/find/europe")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Trip> findTripsEurope(Trip trip) {
        List<Trip> trips = new ArrayList<Trip>();
        for (Place place : europeCities) {
            trip.setArrival(place);
            Trip newTrip = findCheapTrip(trip);
            if (newTrip != null) {
                trips.add(newTrip);
            }
        }
        return trips;

        /*final Lock _mutex = new ReentrantLock(true);
        final List<Trip> trips = new ArrayList<Trip>();
        final List<Thread> threads = new ArrayList<Thread>();
        int i = 0;

        for (final Place place : europeCities) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Trip trip = findCheapTrip(place, departure, arrivalDate, departureDate, seats);;
                    if (trip != null) {
                        _mutex.lock();
                        trips.add(trip);
                        _mutex.unlock();
                    }
                }
            });
            thread.start();
            threads.add(thread);
            i++;
            if (i == 3) {
                Thread.sleep(1000);
                i = 0;
            }
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Collections.sort(trips);
        return trips;*/
    }
}
