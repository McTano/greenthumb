package com.adiga.greenthumbclient;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by dbajj on 2018-03-10.
 */

public abstract class User {
    protected LatLng startLocation;
    protected LatLng destination;

    protected User() {
        startLocation = null;
        destination = null;
    }

    public boolean hasLocation() {
        return startLocation != null;
    }

    public boolean hasDestination() {
        return destination != null;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }
    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

}
