package com.andrius.pathtracking.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.andrius.pathtracking.R;
import com.andrius.pathtracking.tracking.TrackingManager;
import com.andrius.pathtracking.tracking.TrackingService;
import com.andrius.pathtracking.tracking.handlers.TrackHandler;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private AsyncLayoutInflater inflater;
    private LinearLayout llTracks;
    private List<TrackHandler> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            init();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(
                            List<PermissionRequest> permissions, PermissionToken token) {
                    }
                }).check();
    }

    private void init() {
        Switch sw = findViewById(R.id.swTracking);
        if (isMyServiceRunning(TrackingService.class)) sw.setChecked(true);

        sw.setOnClickListener(view -> {
            if (sw.isChecked()) {
                Intent intent = new Intent(this, TrackingService.class);
                ContextCompat.startForegroundService(this, intent);
            } else {
                stopService(new Intent(this, TrackingService.class));
            }
        });

        llTracks = findViewById(R.id.llTracks);
        inflater = new AsyncLayoutInflater(this);

        TrackingManager instance = TrackingManager.getInstance();

        tracks = instance.getHandlers();

        for (TrackHandler track : tracks) {
            addRow(track);
        }

        instance.setListener(() -> runOnUiThread(this::updateCounters));

        findViewById(R.id.btnResetAll).setOnClickListener(view -> {
            for (TrackHandler track : tracks) {
                track.resetFile();
            }
            updateCounters();
        });
    }

    private void updateCounters() {
        for (int i = 0; i < llTracks.getChildCount(); i++) {
            View childAt = llTracks.getChildAt(i);
            TextView tv = childAt.findViewById(R.id.tvCount);
            tv.setText(String.valueOf(tracks.get(i).getLocationCount()));
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void addRow(TrackHandler track) {
        inflater.inflate(R.layout.track_row, llTracks, (view, resId, parent) -> {
            TextView tv = view.findViewById(R.id.tvTitle);
            tv.setText(track.getName());
            view.findViewById(R.id.btnShow).setOnClickListener(v1 -> {
                Intent intent = new Intent(this, MapActivity.class);
                intent.putExtra("trackName", track.getName());
                startActivity(intent);
            });
            view.findViewById(R.id.btnReset).setOnClickListener(v2 -> track.resetFile());
            llTracks.addView(view);
            tv = view.findViewById(R.id.tvCount);
            tv.setText(String.valueOf(track.getLocationCount()));
        });
    }
}
