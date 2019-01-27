package com.andrius.pathtracking.tracking.handlers;

import android.location.Location;

public class SpeedAndHighAccuracyTrack extends TrackHandler {

    @Override
    public void handle(Location location) {
        if (location.getSpeed() > 0) {
            float accuracyMeters = location.getAccuracy();
            if (!(accuracyMeters == 0.0) && !(accuracyMeters > 30)) {
                writeLocation(location);
            }
        }
    }

    @Override
    public String getName() {
        return "SpeedAndHighAccuracyTrack";
    }
}
