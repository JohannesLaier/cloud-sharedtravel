package com.communitravel.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@Ignore
public class TripServiceTest extends ServiceTest {

    public TripServiceTest() {}

    @Test
    public void find() throws IOException {
        JSONArray response = sendRequest("/rest/trip/find", trip);
        assertTrue(response.size() > 0);

        JSONObject jsonTrip = (JSONObject) response.get(0);
        assertNotNull(jsonTrip.get("price"));
        assertNotNull(jsonTrip.get("link"));

    }

    @Test
    public void findEurope() throws IOException {
        JSONArray response = sendRequest("/rest/trip/find/europe", trip);
        assertTrue(response.size() > 0);

        JSONObject jsonTrip = (JSONObject) response.get(0);
        assertNotNull(jsonTrip.get("link"));
        assertNotNull(jsonTrip.get("departureDate"));
        assertNotNull(jsonTrip.get("arrivalDate"));
    }
}
