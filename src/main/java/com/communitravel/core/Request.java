package com.communitravel.core;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.Map;

/**
 * Request - Class to create and send requests to specified APIs
 */
public class Request {
    protected String apiURL;
    protected String apiKey;

    private Cache cache = new Cache();

    public Request(String apiURL, String apiKey) {
        this.apiURL = apiURL;
        this.apiKey = apiKey;
    }

    /**
     * Sends a request to the in apiURL specified API
     * @param Map<String,Object> options The options which can be applied to the service request
     * @return JSONObject Returns the JSONObject which is received by the API response
     */
    public JSONObject sendRequest(Map<String, Object> options) {
        try {

            String params = "?";
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                params += entry.getKey();
                params += "=";
                params += entry.getValue();
                params += "&";
            }

            params = params.substring(0, params.length()-1);

            String url = apiURL + params;
            String output;

            System.out.println(url);

            if (cache.contains(url)) {
                output = cache.get(url);
            } else {

                Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
                WebTarget webTarget = client.target(apiURL + params);

                Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
                invocationBuilder.header("key", apiKey);
                Response response = invocationBuilder.get();
                output = response.readEntity(String.class);

                cache.add(url, output);
            }

            System.out.println(output);

            return (JSONObject) JSONValue.parse(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
