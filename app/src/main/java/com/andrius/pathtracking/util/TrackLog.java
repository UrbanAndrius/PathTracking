package com.andrius.pathtracking.util;

import android.location.Location;
import android.os.Environment;

import org.osmdroid.util.GeoPoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TrackLog {

    private File trackLog;

    public TrackLog(String filename) {
        String name = filename + ".txt";
        trackLog = new File(Environment.getExternalStorageDirectory() + "/logs/", name);
        trackLog.getParentFile().mkdir();
    }

    public void writeLocation(Location location) {
        writeToFile(location.getLatitude() + "\t" + location.getLongitude() + "\n");
    }

    public void resetFile() {
        trackLog.delete();
    }

    private void writeToFile(String text) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(trackLog, true);
            stream.write(text.getBytes());
        } catch (IOException e) {
            AppLog.d(e.toString());
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                AppLog.d(e.toString());
            }
        }
    }

    public int getLocationCount() {
        int counter = 0;

        String content = FileUtils.readFile(trackLog);
        String[] split = content.split("\n");

        for (String s : split) {
            String[] split1 = s.split("\t");
            try {
                new GeoPoint(Double.valueOf(split1[0]), Double.valueOf(split1[1]));
                counter++;
            } catch (NumberFormatException e) {
                return counter;
            }
        }
        return counter;
    }
}
