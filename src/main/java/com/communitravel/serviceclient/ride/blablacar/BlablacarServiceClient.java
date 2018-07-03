package com.communitravel.serviceclient.ride.blablacar;

import com.communitravel.config.Configuration;
import com.communitravel.model.Place;
import com.communitravel.model.Ride;
import com.communitravel.model.SourceProvider;
import com.communitravel.serviceclient.ride.ServiceClientRide;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BlablacarServiceClient - class to request data/rides from BlaBlaCar
 */
public class BlablacarServiceClient extends ServiceClientRide {
    private static String apiKey = "32aeadd52e484bb2965ee22ab182743a";
    private static String apiURL = "https://public-api.blablacar.com/api/v2/trips?key=" + apiKey;

    public BlablacarServiceClient() {
        super(apiURL, apiKey);
    }

    private Place parsePlace(JSONObject placeJSON) {
        Place place = new Place((String) placeJSON.get("city_name"));
        place.setAddress((String) placeJSON.get("address"));
        place.setCountry(Place.Country.valueOf((String) placeJSON.get("country_code")));
        return place;
    }

    private Date parseDate(String dateString) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.GERMAN);
        try {
            if (dateString != null) {
                return df.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double parsePrice(JSONObject priceJSON) {
        return ((Number) priceJSON.get("value")).doubleValue();
    }

    private int parseDuration(JSONObject durationJSON) {
        return ((Number) durationJSON.get("value")).intValue();
    }

    private int parseDistance(JSONObject distanceJSON) {
        return ((Number) distanceJSON.get("value")).intValue();
    }

    private String parseLink(JSONObject linksJSON) {
        return (String) linksJSON.get("_front");
    }

    private List<Ride> parseRides(JSONArray ridesJSON) {
        final List<Thread> threads = new ArrayList<Thread>();
        final List<Ride> rides = new ArrayList<Ride>();
        final Lock _mutex = new ReentrantLock(true);
        for (final Object rideObj : ridesJSON) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                JSONObject rideJSON = (JSONObject) rideObj;

                JSONObject departurePlaceJSON = (JSONObject) rideJSON.get("departure_place");
                Place departurePlace = parsePlace(departurePlaceJSON);

                JSONObject arrivalPlaceJSON = (JSONObject) rideJSON.get("arrival_place");
                Place arrivalPlace = parsePlace(arrivalPlaceJSON);

                JSONObject priceJSON = (JSONObject) rideJSON.get("price_with_commission");
                JSONObject linksJSON = (JSONObject) rideJSON.get("links");
                JSONObject durationJSON = (JSONObject) rideJSON.get("duration");
                JSONObject distanceJSON = (JSONObject) rideJSON.get("distance");
                Ride ride = new Ride(departurePlace, arrivalPlace);
                ride.setPrice(parsePrice(priceJSON));
                ride.setDepartureDate(parseDate((String) rideJSON.get("departure_date")));
                ride.setArrivalDate(parseDate((String) rideJSON.get("arrival_date")));
                ride.setLink(parseLink(linksJSON));
                ride.setDuration(parseDuration(durationJSON));
                ride.setDistance(parseDistance(distanceJSON));
                ride.setSourceProvider(SourceProvider.BlablaCar);

                BlablacarServiceClientDetail detail = new BlablacarServiceClientDetail();
                ride.setDescription(detail.parseDetails((String) rideJSON.get("permanent_id")));

                _mutex.lock();
                rides.add(ride);
                _mutex.unlock();
                }
            });
            threads.add(thread);
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

        return rides;
    }

    @Override
    public List<Ride> findRides(Place departure, Place arrival, Date departureDate, Date arrivalDate, int seats) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("locale", Configuration.instance.countryCode);
            params.put("fn", departure.getName());
            params.put("tn", arrival.getName());
            params.put("_format", "json");
            params.put("db", df.format(departureDate));
            if (departureDate.getTime() < arrivalDate.getTime()) {
                params.put("de", df.format(arrivalDate));
            }
            params.put("seats", seats);
            params.put("cur", Configuration.instance.currency);

            JSONObject result = request.sendRequest(params);
            JSONArray rides = (JSONArray) result.get("trips");

            return parseRides(rides);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Service unreachable");
        }

        return new ArrayList<Ride>();
    }
}
