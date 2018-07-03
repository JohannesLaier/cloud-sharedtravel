package com.communitravel.serviceclient.room.NineFlat;

import com.communitravel.core.Request;
import com.communitravel.model.Host;
import com.communitravel.model.Room;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * NineFlatServiceClientUserPicture - class to request the picture of an user
 */
public class NineFlatServiceClientUserPicture {
    private String apiURL = "https://www.9flats.com/api/v4/users/[SLUG]";
    private String apiKey = "mOmtRJcGOVRa8zdDpkgq4FXYrW0zdvKUIRB3AlP6";
    private Request request;

    /**
     * Parses the received picture to a given room
     * @param String slug the URL of the picture
     * @param Room room object the picture will be parsed to
     * @return Room the room with the received picture
     */
    public Room parseUserPicture(String slug, Room room) {
        Map<String, Object> params = new HashMap<String, Object>();
        apiURL = apiURL.replace("[SLUG]", String.valueOf(slug));
        request = new Request(apiURL, apiKey);

        params.put("client_id", apiKey);

        JSONObject result = request.sendRequest(params);

        String pictureUrl = (String) result.get("avatar");
        room.getHost().setPictureUrl(pictureUrl);
        return room;
    }
}
