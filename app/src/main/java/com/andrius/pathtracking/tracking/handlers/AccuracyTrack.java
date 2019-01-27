package com.andrius.pathtracking.tracking.handlers;

import android.location.Location;

public class AccuracyTrack extends TrackHandler {

    @Override
    public void handle(Location location) {
        float accuracyMeters = location.getAccuracy();
        if (!(accuracyMeters == 0.0) && !(accuracyMeters > 48.1)) {
            writeLocation(location);
        }
    }

    @Override
    public String getName() {
        return "AccuracyTrack";
    }
}
