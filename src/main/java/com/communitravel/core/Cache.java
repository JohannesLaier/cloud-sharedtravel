package com.communitravel.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Cache - Used to store recently received data
 */
public class Cache {
    private static Map<String, CacheEntry> cache = new HashMap<String, CacheEntry>();

    private long expireDate = 5 * 60 * 1000;

    private class CacheEntry {
        private Date creationDate;
        private String content;

        public CacheEntry(String content) {
            this.creationDate = new Date();
            this.content = content;
        }

        /**
         * Checks if an entry exists for more than five minutes
         * @return Return true if the entry exists for the duration of expireDate
         */
        public boolean isExpired() {
            Date now = new Date();
            if (now.getTime() - creationDate.getTime() < expireDate) {
                return false;
            }
            return true;
        }

        public String getContent() {
            return content;
        }
    }

    /**
     * Adds a new entry to the cache
     * @param String url The website the data comes from
     * @param String content The data that has to be cached
     */
    public void add(String url, String content) {
        cache.put(url, new CacheEntry(content));
    }

    public String get(String url) {
        CacheEntry entry = cache.get(url);
        if (entry != null) {
            if (entry.isExpired()) {
                cache.remove(url);
                return null;
            }
            return entry.getContent();
        }
        return null;
    }

    /**
     * Checks if a entry of a specified url is stored in the cache
     * @param String url The URL of the wanted entry
     * @return boolean Returns true if an entry with the specified url exists in the cache
     */
    public boolean contains(String url) {
        return get(url) != null;
    }

    /**
     * Cleans the cache, romoves al content
     */
    public static void clean() {
        cache = new HashMap<String, CacheEntry>();
    }
}
