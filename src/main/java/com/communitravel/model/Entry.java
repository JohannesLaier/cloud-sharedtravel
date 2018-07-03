package com.communitravel.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Entry - abstract class to create a general structure for rooms and rides
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Entry {

    protected int id;
    protected String link;
    public abstract double getPrice();

    public void setLink(String link) {
        this.link = link;
    }
}
