package com.andrius.pathtracking.track_display;

import android.os.Environment;

import com.andrius.pathtracking.util.FileUtils;

import org.osmdroid.util.GeoPoint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TrackRetriever {

    private File track;

    public TrackRetriever(String trackName) {
        track = new File(Environment.getExternalStorageDirectory() + "/logs/", trackName + ".txt");
    }

    public List<GeoPoint> getTrack() {
        List<GeoPoint> geoPoints = new ArrayList<>();

        String content = FileUtils.readFile(track);
        String[] split = content.split("\n");

        for (String s : split) {
            String[] split1 = s.split("\t");
            try {
                geoPoints.add(new GeoPoint(Double.valueOf(split1[0]), Double.valueOf(split1[1])));
            } catch (NumberFormatException e) {
                return geoPoints;
            }
        }
        return geoPoints;
    }
}
