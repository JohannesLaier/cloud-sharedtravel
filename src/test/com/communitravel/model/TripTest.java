package com.communitravel.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TripTest {

    private Trip trip;
    private Room room;

    private Place from;
    private Place to;

    private Ride rideFrom;
    private Ride rideTo;

    private Date arrivalDate;
    private Date departureRide;

    private void restore() {
        from = new Place("Berlin");
        to = new Place("Hamburg");

        this.trip = new Trip(to, from);
        trip.setSeats(5);

        arrivalDate = new Date(2017, 05, 10);
        departureRide = new Date(2017, 04, 31);

        room = new Room();
        room.setPlace(to);
        room.setPrice(74.95);
        room.setCheckIn(departureRide);
        room.setCheckOut(arrivalDate);
        trip.setRoom(room);

        rideFrom = new Ride(from, to);
        rideFrom.setPrice(19.95);

        rideTo = new Ride(to, from);
        rideTo.setPrice(24.95);

        trip.setArrivalRide(rideTo);
        trip.setDepartureRide(rideFrom);
    }

    @Test
    public void getArrival() throws Exception {
        restore();
        assertEquals(from.getName(), trip.getArrival().getName());
    }

    @Test
    public void getDeparture() throws Exception {
        restore();
        assertEquals(to.getName(), trip.getDeparture().getName());
    }

    @Test
    public void getPrice() throws Exception {
        restore();
        assertEquals(794.40, trip.getPrice(),0.01);
    }
}