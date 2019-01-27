package com.andrius.pathtracking.tracking;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.andrius.pathtracking.App;
import com.andrius.pathtracking.R;
import com.andrius.pathtracking.util.AppLog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import androidx.core.app.NotificationCompat;

public class TrackingService extends Service {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private TrackingManager trackingManager;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.d("onCreate");

        mFusedLocationClient = new FusedLocationProviderClient(this);
        trackingManager = TrackingManager.getInstance();

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(1000);
        locationRequest.setInterval(2000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                AppLog.d("loc: " + locationResult.getLastLocation().getLongitude());
                trackingManager.handleLocation(locationResult.getLastLocation());
            }
        };

        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppLog.d("onStartCommand");

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background).build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLog.d("onDestroy");
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
