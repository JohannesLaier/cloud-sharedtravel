package com.communitravel.serviceclient;

import com.communitravel.core.Request;

public abstract class ServiceClient {
    protected Request request;

    public ServiceClient(String apiURL, String apiKey) {
        request = new Request(apiURL, apiKey);
    }
}
