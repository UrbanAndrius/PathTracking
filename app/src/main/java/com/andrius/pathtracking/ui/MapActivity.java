package com.andrius.pathtracking.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

import com.andrius.pathtracking.R;
import com.andrius.pathtracking.track_display.TrackRetriever;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private Polyline polyline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        findViewById(R.id.btnRefresh).setOnClickListener(view -> refreshTrack());

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setZoom(14f);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);

        polyline = new Polyline(mapView);
        polyline.getPaint().setStrokeCap(Paint.Cap.ROUND);
        polyline.setColor(Color.BLUE);
        mapView.getOverlayManager().add(polyline);

        refreshTrack();
    }

    private void refreshTrack() {
        String trackName = getIntent().getStringExtra("trackName");
        if (trackName == null || trackName.isEmpty()) return;

        List<GeoPoint> track = new TrackRetriever(trackName).getTrack();

        if (track.size() > 0) {
            mapView.getController().setCenter(track.get(0));
        } else {
            Toast.makeText(this, "Empty track", Toast.LENGTH_SHORT).show();
            mapView.getController().setCenter(new GeoPoint(55f, 25f));
        }

        polyline.setPoints(track);
        mapView.invalidate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
