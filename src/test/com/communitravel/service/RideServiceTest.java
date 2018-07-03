package com.communitravel.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class RideServiceTest extends  ServiceTest {
    @Test
    public void find() throws IOException {
        JSONArray response = sendRequest("/rest/ride/find", trip);
        assertTrue(response.size() > 0);

        JSONObject jsonTrip = (JSONObject) response.get(0);
        assertNotNull(jsonTrip.get("link"));
        assertNotNull(jsonTrip.get("departureDate"));
        assertNotNull(jsonTrip.get("arrivalDate"));
        assertNotNull(jsonTrip.get("source"));
        assertNotNull(jsonTrip.get("destination"));
    }
}
