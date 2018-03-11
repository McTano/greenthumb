package com.adiga.greenthumbclient;

/**
 * Created by dbajj on 2018-03-10.
 */

public class Rider extends User {

    public Rider() {
    }

    @Override
    public boolean getIsDriver() {
        return false;
    }

    @Override
    public int getSeats() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getVehicle() {
        return "";
    }


}
