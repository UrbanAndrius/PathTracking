package com.andrius.pathtracking.tracking.handlers;

import android.location.Location;

public class SimpleTrack extends TrackHandler {

    @Override
    public void handle(Location location) {
        writeLocation(location);
    }

    @Override
    public String getName() {
        return "SimpleTrack";
    }
}
