package com.communitravel.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class RoomTest {
    @Test
    public void getPrice() throws Exception {

        Room room = new Room();
        room.setPlace(new Place("München"));
        room.setPrice(49.95);
        room.setCheckIn(new Date(2017, 04, 31));
        room.setCheckOut(new Date(2017, 05, 03));

        assertEquals(149.85, room.getPrice(), 0.01);
    }
}