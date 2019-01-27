package com.andrius.pathtracking.tracking.handlers;

import android.location.Location;

public class SpeedTrack extends TrackHandler {

    @Override
    public void handle(Location location) {
        if (location.getSpeed() != 0) {
            writeLocation(location);
        }
    }

    @Override
    public String getName() {
        return "SpeedTrack";
    }
}
