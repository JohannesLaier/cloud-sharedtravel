package com.communitravel.serviceclient.room.airbnb;

import com.communitravel.config.Configuration;
import com.communitravel.model.Host;
import com.communitravel.model.Place;
import com.communitravel.model.Room;
import com.communitravel.model.SourceProvider;
import com.communitravel.serviceclient.room.ServiceClientRoom;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* AirbnbServiceClient - class to request data/rooms from AirBnB
*/
public class AirbnbServiceClient extends ServiceClientRoom {
    private static String apiURL = "https://api.airbnb.com/v2/search_results";
    private static String apiKey = "3092nxybyb0otqw18e8nh5nty";

    public AirbnbServiceClient() {
        super(apiURL, apiKey);
    }

    /**
     * Parses the received data to a "Room" object
     * @param JSONObject listing the data received from AirBnB
     * @param Room room object the data will be parsed to
     * @return Room the room with the received data
     */
    public Room parseListing(JSONObject listing, Room room) {
        String city = (String) listing.get("city");
        String name = (String) listing.get("name");
        Number id = (Number) listing.get("id");
        Number capacity = (Number) listing.get("person_capacity");
        Number countOfBedrooms = (Number) listing.get("bedrooms");
        Number countOfBathrooms = (Number) listing.get("bathrooms");
        Number countOfBeds = (Number) listing.get("beds");
        Number rating = (Number) listing.get("star_rating");
        String address = (String) listing.get("public_address");

        JSONArray pictures = (JSONArray) listing.get("xl_picture_urls");
        room = parsePictures(pictures, room);

        JSONObject user = (JSONObject) listing.get("primary_host");
        room = parseHost(user, room);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String link = "https://www.airbnb.de/rooms/" + id
                + "?checkin=" + df.format(room.getCheckIn())
                + "&checkout=" + df.format(room.getCheckOut())
                + "&guests=" + room.getGuests();

        room.setId(id.intValue());
        room.setLink(link);
        room.setCapacity(capacity.intValue());
        room.setCountOfBedrooms(countOfBedrooms.intValue());
        room.setCountOfBathrooms(countOfBathrooms.intValue());
        room.setCountOfBeds(countOfBeds.intValue());
        room.setName(name);
        room.setPlace(new Place(city));
        room.setAddress(address);
        if (rating == null) {
            room.setRating(0.0);
        } else {
            room.setRating(rating.doubleValue());
        }

        return room;
    }

    /**
     * Parses the received price to the given room object
     * @param JSONObject pricingQuote price received from AirBnB
     * @param Room room object the data will be parsed to
     * @return Room the room with the received data
     */
    public Room parsePricingQuote(JSONObject pricingQuote, Room room) {
        Number price = ((Number) pricingQuote.get("localized_nightly_price"));
        room.setPrice(price.doubleValue());
        return room;
    }

    /**
     * Parses the received host to the given room object
     * @param JSONObject host host received from AirBnB
     * @param Room room object the data will be parsed to
     * @return Room the room with the received data
     */
    public Room parseHost(JSONObject host, Room room) {
        String name = (String) host.get("first_name");
        String pictureUrl = (String) host.get("picture_url");
        room.setHost(new Host(name, pictureUrl));
        return room;
    }

    /**
     * Parses the received Array of pictures to the given room object
     * @param JSONArray pictures received from AirBnB
     * @param Room room object the pictures will be parsed to
     * @return Room the room with the received pictures
     */
    public Room parsePictures(JSONArray pictures, Room room) {
        Set<String> links = new HashSet<String>();
        for(Object picture : pictures) {
            links.add((String)picture);
        }

        room.setPictures(links);
        return room;
    }

    /**
     * Parses the received data to a list of rooms
     * @param JSONArray searchResults results of a search
     * @param Date arrivalDate date of arrival
     * @param Date departureDate date of departure
     * @param int guests number of guests
     * @return List<Room> rooms the received data
     */
    public List<Room> parseResults(JSONArray searchResults, final Date arrivalDate, final Date departureDate, final int guests) {
        final List<Room> rooms = new ArrayList<Room>();
        final List<Thread> threads = new ArrayList<Thread>();
        final Lock _mutex = new ReentrantLock(true);
        for(final Object searchResult : searchResults) {
            threads.add(new Thread(new Runnable() {
                public void run() {
                JSONObject result = (JSONObject) searchResult;
                JSONObject pricingQuote = (JSONObject) result.get("pricing_quote");
                JSONObject listing = (JSONObject) result.get("listing");

                Room room = new Room();
                room.setSourceProvider(SourceProvider.Airbnb);
                room.setCheckIn(arrivalDate);
                room.setCheckOut(departureDate);
                room.setGuests(guests);
                room = parseListing(listing, room);
                room = parsePricingQuote(pricingQuote, room);
                AirbnbServiceClientDetail detail = new AirbnbServiceClientDetail();
                room = detail.parseDetails(room.getId(), room);

                _mutex.lock();
                rooms.add(room);
                _mutex.unlock();
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return rooms;
    }

    /**
     * Searches for rooms with the defined properties
     * @param Place place place of destination
     * @param Date arrivalDate date of arrival
     * @param Date departureDate date of departure
     * @param int guests number of guests
     * @return List<Room> the rooms with the specified data
     */
    @Override
    public List<Room> findRooms(Place place, Date arrivalDate, Date departureDate, int guests) {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("client_id", apiKey);
            params.put("location", URLEncoder.encode(place.getName(), "UTF-8"));
            params.put("checkout", df.format(departureDate));
            params.put("checkin", df.format(arrivalDate));
            params.put("guests", guests);
            params.put("cur", Configuration.instance.currency);

            JSONObject result = request.sendRequest(params);

            JSONArray searchResults = (JSONArray) result.get("search_results");
            return parseResults(searchResults, arrivalDate, departureDate, guests);

        } catch (Exception e) {
            System.out.println("Service unreachable");
            e.printStackTrace();
        }

        return new ArrayList<Room>();
    }
}
