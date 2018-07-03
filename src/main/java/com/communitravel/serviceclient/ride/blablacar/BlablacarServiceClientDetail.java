package com.communitravel.serviceclient.ride.blablacar;

import com.communitravel.core.Request;
import org.json.simple.JSONObject;
import java.util.*;

/**
 * BlablacarServiceClientDetail - class to request additional details
 */
public class BlablacarServiceClientDetail {
    private String apiURL = "https://public-api.blablacar.com/api/v2/trips/[ID]?locale=de_DE&_format=json";
    private String apiKey = "32aeadd52e484bb2965ee22ab182743a";
    private Request request;

    /**
     * Gets additional details to a given room
     * @param String permanentID ID of the room
     * @return String Returns the additional data
     */
    public String parseDetails(String permanentId) {
        Map<String, Object> params = new HashMap<String, Object>();
        apiURL = apiURL.replace("[ID]", String.valueOf(permanentId));
        request = new Request(apiURL, apiKey);

        params.put("key", apiKey);

        JSONObject result = request.sendRequest(params);
        try {
            return (String) result.get("comment");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
