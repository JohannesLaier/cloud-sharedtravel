package com.communitravel.serviceclient.room.airbnb;

import com.communitravel.core.Request;
import com.communitravel.model.Room;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;

/**
 * AirbnbServiceClientDetail - programm to get the description to a room
 */
public class AirbnbServiceClientDetail {
    private String apiURL = "https://api.airbnb.com/v2/listings/[LISTINGID]";
    private String apiKey = "3092nxybyb0otqw18e8nh5nty";
    private Request request;

    /**
     * Parses the received details to a given room
     * @param int listingID the ID of the room
     * @param Room room object the details will be parsed to
     * @return Room the room with the received details
     */
    public Room parseDetails(int listingId, Room room) {
        Map<String, Object> params = new HashMap<String, Object>();
        apiURL = apiURL.replace("[LISTINGID]", String.valueOf(listingId));
        request = new Request(apiURL, apiKey);

        params.put("client_id", apiKey);
        params.put("_format", "v1_legacy_for_p3");

        JSONObject listing = new JSONObject();

        JSONObject result = request.sendRequest(params);
        try {
            listing = (JSONObject) result.get("listing");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseListing(listing, room);
    }

    /**
     * Parses the received details to a given room
     * @param JSONObject listing the details received from AirBnB
     * @param Room room object the details will be parsed to
     * @return Room the room with the received details
     */
    public Room parseListing(JSONObject listing, Room room) {
        String description = (String) listing.get("description");
        room.setDescription(description);
        return room;
    }
}
