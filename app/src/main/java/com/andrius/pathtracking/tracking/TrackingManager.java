package com.andrius.pathtracking.tracking;

import android.location.Location;

import com.andrius.pathtracking.tracking.handlers.AccuracyTrack;
import com.andrius.pathtracking.tracking.handlers.SimpleTrack;
import com.andrius.pathtracking.tracking.handlers.SpeedAndHighAccuracyTrack;
import com.andrius.pathtracking.tracking.handlers.SpeedTrack;
import com.andrius.pathtracking.tracking.handlers.TrackHandler;

import java.util.ArrayList;
import java.util.List;

public class TrackingManager {

    private List<TrackHandler> handlers = new ArrayList<>();
    private Listener listener;

    private static TrackingManager instance;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onLocationHandle();
    }

    public static synchronized TrackingManager getInstance() {
        if (instance == null) {
            instance = new TrackingManager();
        }
        return instance;
    }

    private TrackingManager() {
        handlers.add(new SimpleTrack());
        handlers.add(new SpeedTrack());
        handlers.add(new AccuracyTrack());
        handlers.add(new SpeedAndHighAccuracyTrack());
    }

    public void handleLocation(Location location) {
        for (TrackHandler handler : handlers) {
            handler.handle(location);
        }
        if (listener != null) listener.onLocationHandle();
    }

    public void resetFiles() {
        for (TrackHandler handler : handlers) {
            handler.resetFile();
        }
    }

    public List<TrackHandler> getHandlers() {
        return handlers;
    }

}
