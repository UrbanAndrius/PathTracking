package com.andrius.pathtracking.tracking.handlers;

import android.location.Location;

import com.andrius.pathtracking.util.TrackLog;

public abstract class TrackHandler {

    private TrackLog trackLog;
    private int locationCount;

    TrackHandler() {
        trackLog = new TrackLog(getName());
        locationCount = trackLog.getLocationCount();
    }

    public abstract void handle(Location location);

    public abstract String getName();

    public void resetFile() {
        trackLog.resetFile();
        locationCount = 0;
    }

    protected void writeLocation(Location location) {
        trackLog.writeLocation(location);
        locationCount++;
    }

    public int getLocationCount() {
        return locationCount;
    }

}
