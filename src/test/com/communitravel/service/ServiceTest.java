package com.communitravel.service;

import com.communitravel.model.Place;
import com.communitravel.model.Trip;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

public class ServiceTest {
    private static short JETTY_PORT = 9999;
    private static String JETTY_URL = "http://127.0.0.1:" + JETTY_PORT;

    private Server server;
    protected Trip trip;

    public ServiceTest() {}

    @Before
    public void start() throws Exception {
        server = new Server(JETTY_PORT);
        final String rootPath = ServiceTest.class.getClassLoader().getResource(".").toString();
        final String webAppPath = rootPath + "../../src/main/webapp";
        final WebAppContext context = new WebAppContext();
        context.setWar(webAppPath);
        server.setHandler(context);
        server.start();

        init();
    }

    protected void init() {
        Date departureDate = new Date();
        Date arrivalDate = new Date(departureDate.getTime() + (1000 * 60 * 60 * 24 * 7));

        trip = new Trip(new Place("Berlin"), new Place("Hamburg"));
        trip.setDepartureDate(departureDate);
        trip.setArrivalDate(arrivalDate);
        trip.setSeats(1);
    }

    public JSONArray sendRequest(String url, Trip postData) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(JETTY_URL + url);

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("content-type", "application/json");
        Response response = invocationBuilder.post(Entity.entity(postData, MediaType.APPLICATION_JSON_TYPE));
        String output = response.readEntity(String.class);

        System.out.print("[Unit-Test] Output: " + output);
        return (JSONArray) JSONValue.parse(output);
    }

    @After
    public void stop() throws Exception {
        server.stop();
    }
}
