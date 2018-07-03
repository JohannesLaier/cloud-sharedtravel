package com.communitravel.model;

import java.util.Date;

/**
 * Ride - object of a possible ride received by an API
 */
public class Ride extends Entry implements Comparable<Ride> {
    private Date departureDate;
    private Date arrivalDate;

    private int duration;
    private int distance;

    private String description;

    private Place source;
    private Place destination;

    private double price;

    private SourceProvider sourceProvider;

    public Ride(Place source, Place destination) {
        this.source = source;
        this.destination = destination;
    }

    public Ride(Place source, Place destination, Date departureDate, Date arrivalDate) {
        this(source, destination);
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String toString() {
        return "[Source: " + source + ", Destination: " + destination + ", Price: " + price + "]";
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }

    public Date getArrivalDate() {
        return this.arrivalDate;
    }

    public int compareTo(Ride ride) {
        if (this.price < ride.price) {
            return -1;
        } else if(this.price > ride.price){
            return 1;
        }
        return 0;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() { return this.duration; }

    public int getDistance() { return this.distance; }

    public String getDescription() { return this.description; }

    public Place getSource() {
        return source;
    }

    public Place getDestination() {
        return destination;
    }

    public void setSourceProvider(SourceProvider sourceProvider) {
        this.sourceProvider = sourceProvider;
    }
}
