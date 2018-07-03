package com.communitravel.serviceclient.ride.bessermitfahren;

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

public class BessermitfahrenServiceClient extends ServiceClientRide {
    private static String apiKey = "d5f9cedf5676c9634159b78d1c60183f";
    private static String apiURL = "https://api.bessermitfahren.de/" + apiKey;


    public BessermitfahrenServiceClient() {
        super(apiURL, apiKey);
    }

    private Ride parseDay(JSONArray day, long timestamp, Place departure, Place arrival) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String url = (String) day.get(0);
        Double price = Double.valueOf(day.get(3).toString());
        Long emptySeats = Long.valueOf(day.get(4).toString());

        DateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.GERMAN);
        JSONArray startArray = (JSONArray) day.get(1);
        String startTimeString =  df.format(timestamp) + " " + startArray.get(0);
        Date startTime = timeFormater.parse(startTimeString);

        JSONArray stopArray = (JSONArray) day.get(2);
        String stopTimeString =  df.format(timestamp) + " " + stopArray.get(0);
        Date stopTime = timeFormater.parse(stopTimeString);

        int duration = (int) (stopTime.getTime() - startTime.getTime()) / 1000;

        departure.setAddress(departure.getName());
        arrival.setAddress(arrival.getName());

        Ride ride = new Ride(departure, arrival);
        ride.setDepartureDate(startTime);
        ride.setArrivalDate(stopTime);
        ride.setPrice(price);
        ride.setLink(url);
        ride.setDuration(duration);
        ride.setSourceProvider(SourceProvider.Bessermitfahren);

        return ride;
    }

    @Override
    public List<Ride> findRides(Place departure, Place arrival, Date departureDate, Date arrivalDate, int seats) {
        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("from", departure.getName());
            params.put("to", arrival.getName());
            params.put("date", df.format(departureDate.getTime()));

            List<Ride> rides = new ArrayList<Ride>();

            JSONObject result = request.sendRequest(params);
            JSONObject resultset = (JSONObject) result.get("resultset");
            for (Object entry : resultset.entrySet()) {
                Map.Entry<String, JSONArray> resultDay = (Map.Entry<String, JSONArray>) entry;
                long timestamp = Long.valueOf(resultDay.getKey()) * 1000;
                JSONArray dayList = resultDay.getValue();
                for (Object obj : dayList) {
                    rides.add(parseDay((JSONArray) obj, timestamp, departure, arrival));
                }
            }

            return rides;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Ride>();
    }
}
