package com.communitravel.model;

import java.util.Date;

/**
 * Trip - class to create a trip which contains two rides and a room
 */
public class Trip extends Entry implements Comparable<Trip> {
    private Ride departureRide;
    private Ride arrivalRide;
    private Date departureDate;
    private Date arrivalDate;
    private Room room;
    private Place departure;
    private Place arrival;
    private int seats;

    public Trip() {}

    public Trip(Place departure, Place arrival) {
        this.arrival = arrival;
        this.departure = departure;
    }

    public void setDepartureRide(Ride departureRide) {
        this.departureRide = departureRide;
    }

    public void setArrivalRide(Ride arrivalRide) {
        this.arrivalRide = arrivalRide;
    }

    public void setArrival(Place arrival) {
        this.arrival = arrival;
    }

    public void setDepartureDate(Date date) {
        this.departureDate = date;
    }

    public void setArrivalDate(Date date) {
        this.arrivalDate = date;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getSeats() {
        return this.seats;
    }

    public Place getArrival() {
        return this.arrival;
    }

    public Place getDeparture() {
        return this.departure;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }

    public Date getArrivalDate() {
        return this.arrivalDate;
    }

    @Override
    public double getPrice() {
        return departureRide.getPrice() + arrivalRide.getPrice() + room.getPrice();
    }

    public int compareTo(Trip trip) {
        if (this.getPrice() < trip.getPrice()) {
            return -1;
        } else if(this.getPrice() > trip.getPrice()) {
            return 1;
        }
        return 0;
    }

    public void setDeparture(Place departure) {
        this.departure = departure;
    }
}
