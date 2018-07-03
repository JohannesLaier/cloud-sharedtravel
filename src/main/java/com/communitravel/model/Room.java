package com.communitravel.model;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Room - object of a possible room received by an API
 */
public class Room extends Entry implements Comparable<Room> {

    private Host host;
    private Date checkIn;
    private Date checkOut;
    private Place place;
    private int guests;
    private String address;

    private double price;
    private String name;
    private String description;
    private int id;
    private int capacity;
    private int countOfBeds;
    private int countOfBedrooms;
    private int countOfBathrooms;
    private double rating;
    private Set<String> links;

    private SourceProvider sourceProvider;

    @Override
    public double getPrice() {
        long end = checkOut.getTime();
        long start = checkIn.getTime();
        long duration = (end - start) / 1000 / 60 / 60 / 24;
        return price * duration;
    }

    public void setSourceProvider(SourceProvider sourceProvider) { this.sourceProvider = sourceProvider; }

    public void setHost(Host host) { this.host = host; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public void setCountOfBeds(int countOfBeds) {
        this.countOfBeds = countOfBeds;
    }

    public void setCountOfBedrooms(int countOfBedrooms) {
        this.countOfBedrooms = countOfBedrooms;
    }

    public void setCountOfBathrooms(int countOfBathrooms) {
        this.countOfBathrooms = countOfBathrooms;
    }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public void setAddress(String address) { this.address = address; }

    public void setRating(double rating) { this.rating = rating; }

    public void setPictures(Set<String> links) {
        this.links = links;
    }

    public Date getCheckIn() { return checkIn; }

    public Date getCheckOut() { return checkOut; }

    public int getGuests() { return guests; }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public double getRating() { return rating; }

    public Host getHost() { return this.host; }

    public int getId() { return id; }

    public Place getPlace() {
        return place;
    }

    public void setPrice(double price) { this.price = price; }

    public int compareTo(Room room) {
        if (this.price < room.price) {
            return -1;
        } else if(this.price > room.price) {
            return 1;
        }
        return 0;
    }
}
