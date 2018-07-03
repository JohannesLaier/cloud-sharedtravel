package com.communitravel.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void add() throws Exception {
        Cache.clean();

        String url = "http://test.com/rest/url";
        String json = "[{name:'abc'},{name:'def'}]";

        Cache cache = new Cache();
        cache.add(url, json);

        assertEquals(json, cache.get(url));
    }

    @Test
    public void get() throws Exception {
        Cache.clean();

        String url = "http://test.com/rest/url";
        String json = "[{name:'abc'},{name:'def'}]";

        Cache cache = new Cache();

        assertNull(cache.get(url));

        cache.add(url, json);

        assertEquals(json, cache.get(url));

        String url2 = "http://abc.com/rest/data/get";
        String json2 = "[{id:1},{id:2}]";

        cache.add(url2, json2);

        assertEquals(json, cache.get(url));
        assertEquals(json2, cache.get(url2));
    }

    @Test
    public void contains() throws Exception {
        Cache.clean();

        String url = "http://test.com/rest/url";
        String json = "[{name:'abc'},{name:'def'}]";

        String url2 = "http://abc.com/rest/data/get";
        String json2 = "[{id:1},{id:2}]";

        Cache cache = new Cache();

        assertFalse(cache.contains(url));
        assertFalse(cache.contains(url2));

        cache.add(url, json);

        assertTrue(cache.contains(url));
        assertFalse(cache.contains(url2));

        cache.add(url2, json2);

        assertTrue(cache.contains(url));
        assertTrue(cache.contains(url2));
    }
}