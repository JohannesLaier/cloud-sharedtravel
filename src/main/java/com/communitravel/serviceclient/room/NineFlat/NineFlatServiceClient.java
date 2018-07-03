package com.communitravel.serviceclient.room.NineFlat;

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
 * NineFlatServiceClient - class to request data/rooms from 9Flat
 */
public class NineFlatServiceClient extends ServiceClientRoom {

    private static String apiURL = "https://www.9flats.com/api/v4/places";
    private static String apiKey = "mOmtRJcGOVRa8zdDpkgq4FXYrW0zdvKUIRB3AlP6";

    public NineFlatServiceClient() {
        super(apiURL, apiKey);
    }

    /**
     * Parses the received price to the given room object
     * @param JSONObject pricingQuote price received from AirBnB
     * @param Room room object the data will be parsed to
     * @return Room the room with the received data
     */
    public Room parsePricing(JSONObject pricing, Room room) {
        Number price = (Number) pricing.get("price");
        room.setPrice(price.doubleValue());
        return room;
    }

    /**
     * Parses the received Array of pictures to the given room object
     * @param JSONArray pictures received from AirBnB
     * @param Room room object the pictures will be parsed to
     * @return Room the room with the received pictures
     */
    public Room parsePictures(JSONObject featuredPicture, JSONArray pictures, Room room) {
        Set<String> links = new HashSet<String>();

        links.add((String) featuredPicture.get("medium"));

        for(Object object : pictures) {
            JSONObject picture = (JSONObject) object;
            JSONObject pic = (JSONObject) picture.get("place_photo");
            links.add((String) pic.get("url"));
        }

        room.setPictures(links);
        return room;

    }

    /**
     * Parses the received host to the given room object
     * @param JSONObject host host received from AirBnB
     * @param Room room object the data will be parsed to
     * @return Room the room with the received data
     */
    public Host parseHost(JSONObject host) {
        String name = (String) host.get("name");
        String slug = (String) host.get("slug");

        Host newHost = new Host();
        newHost.setName(name);
        newHost.setSlug(slug);
        return newHost;
    }

    /**
     * Parses the received details to a "Room" object
     * @param JSONObject details the details that are to be parsed
     * @param Room room object the data will be parsed to
     * @return Room the room with the received data
     */
    public Room parseDetails(JSONObject details, Room room) {
        String name = (String) details.get("name");
        Number countOfBeds = (Number) details.get("number_of_beds");
        Number countOfBedrooms = (Number) details.get("number_of_bedrooms");
        Number countOfBathrooms = (Number) details.get("number_of_bathrooms");
        String description = (String) details.get("description");
        Number rating = (Number) details.get("rating");
        Number id = (Number) details.get("id");
        String city = (String) details.get("city");
        JSONObject host = (JSONObject) details.get("host");
        JSONArray pictures = (JSONArray) details.get("additional_photos");
        JSONObject featuredPicture = (JSONObject) details.get("featured_photo");

        room.setName(name);
        if (countOfBathrooms != null) {
            room.setCountOfBathrooms(countOfBathrooms.intValue());
        }
        if (countOfBedrooms != null) {
            room.setCountOfBedrooms(countOfBedrooms.intValue());
        }
        if (countOfBeds != null) {
            room.setCountOfBeds(countOfBeds.intValue());
        }

        room.setDescription(description);
        room.setRating(rating.doubleValue());
        room.setId(id.intValue());
        room.setPlace(new Place(city));
        room.setHost(parseHost(host));

        room = parsePictures(featuredPicture, pictures, room);
        room.setLink("https://www.9flats.com/de/places/" + room.getId());

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
                JSONObject place = (JSONObject) result.get("place");
                JSONObject place_details = (JSONObject) place.get("place_details");
                JSONObject pricing = (JSONObject) place.get("pricing");

                Room room = new Room();
                room.setSourceProvider(SourceProvider.NineFlats);
                room.setCheckIn(arrivalDate);
                room.setCheckOut(departureDate);
                room.setGuests(guests);
                room = parseDetails(place_details, room);
                room = parsePricing(pricing, room);
                NineFlatServiceClientUserPicture user = new NineFlatServiceClientUserPicture();
                room = user.parseUserPicture(room.getHost().getSlug(), room);

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
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("client_id", apiKey);
            params.put("search[query]", URLEncoder.encode(place.getName(), "UTF-8"));
            params.put("search[start_date]", df.format(departureDate));
            params.put("search[end_date]", df.format(arrivalDate));
            params.put("search[number_of_beds]", guests);
            params.put("search[per_page]", 20);
            params.put("currency", Configuration.instance.currency);

            JSONObject result = request.sendRequest(params);

            JSONArray searchResults = (JSONArray) result.get("places");
            return parseResults(searchResults, arrivalDate, departureDate, guests);

        } catch (Exception e) {
            System.out.println("Service unreachable");
            e.printStackTrace();
        }

        return new ArrayList<Room>();
    }
}
