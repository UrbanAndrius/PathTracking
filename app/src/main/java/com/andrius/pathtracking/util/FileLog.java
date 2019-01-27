package com.andrius.pathtracking.util;

import android.location.Location;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FileLog {

    private File logFile;

    public FileLog() {
        logFile = new File(Environment.getExternalStorageDirectory(), "log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                AppLog.d(e.toString());
            }
        }
    }

    public void logLocation(Location location) {
        Date currentTime = Calendar.getInstance().getTime();
        String time = new SimpleDateFormat("HH:mm:ss ", Locale.ENGLISH).format(currentTime);
        String loc = "[" + location.getLatitude() + " ; " + location.getLongitude() + "] ";
        String speed = "Speed: " + location.getSpeed() + ", ";
        String accuracy = "Accuracy: " + location.getAccuracy() + ", ";
        writeToFile(time + loc + speed + accuracy + "\n", true );
    }

    private void writeToFile(String text, boolean append) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(logFile, append);
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
}
