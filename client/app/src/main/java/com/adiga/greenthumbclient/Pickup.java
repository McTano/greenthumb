package com.adiga.greenthumbclient;

/**
 * Created by dbajj on 2018-03-10.
 */

public class Pickup {

    private String time;
    private String location;

    public Pickup(String location, String time) {
        this.time = time;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }
}
