package com.communitravel.model;

import java.util.List;

/**
 * TripMerge - class used for creating sales
 */
public class TripMerge {

    private List<Ride> ridesOnDepartureDate;
    private List<Ride> ridesOnArrivalDate;
    private List<Room> rooms;

    public TripMerge(List<Ride> ridesOnDepartureDate, List<Ride> ridesOnArrivalDate, List<Room> rooms) {
        this.ridesOnDepartureDate = ridesOnDepartureDate;
        this.ridesOnArrivalDate = ridesOnArrivalDate;
        this.rooms = rooms;
    }

    public List<Ride> getRidesOnDepartureDate() {
        return this.ridesOnDepartureDate;
    }

    public List<Ride> getRidesOnArrivalDate() {
        return this.ridesOnArrivalDate;
    }

    public List<Room> getRooms() {
        return this.rooms;
    }
}