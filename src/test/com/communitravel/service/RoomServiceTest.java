package com.communitravel.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class RoomServiceTest extends ServiceTest {

    public RoomServiceTest() {}

    @Test
    public void find() throws IOException {
        JSONArray response = sendRequest("/rest/room/find", trip);
        assertTrue(response.size() > 0);

        JSONObject jsonTrip = (JSONObject) response.get(0);
        assertNotNull(jsonTrip.get("id"));
        assertNotNull(jsonTrip.get("host"));
        assertNotNull(jsonTrip.get("link"));
    }
}
