package com.adiga.greenthumbclient;

/**
 * Created by dbajj on 2018-03-10.
 */

public class Driver extends User  {
    private int carSeats;
    private String licensePlate;
    private int id = 400;

    public Driver() {
        super();
    }

    @Override
    public boolean getIsDriver() {
        return true;
    }

    @Override
    public int getSeats() {
        return this.carSeats;
    }

    @Override
    public int getId() {
        return this.getId();
    }

    @Override
    public String getVehicle() {
        return "911 DAFUZZ";
    }

    public int getCarSeats() {
        return carSeats;
    }

    public void setCarSeats(int carSeats) {
        this.carSeats = carSeats;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
