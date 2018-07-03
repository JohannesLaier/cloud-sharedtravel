package com.communitravel.model;

/**
 * Host - class of objects to store a host of which a place can be rent
 */
public class Host {
    private String slug; //only needed for 9flat
    private String name;
    private String pictureUrl;

    public Host(String name, String pictureUrl) {
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public Host() {}

    public void setName(String name) { this.name = name; }

    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    public void setSlug(String slug) { this.slug = slug; }

    public String getSlug() { return this.slug; }

    public String getName() {
        return this.name;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

}
