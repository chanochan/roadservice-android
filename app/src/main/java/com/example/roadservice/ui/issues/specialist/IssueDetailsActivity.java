package com.example.roadservice.ui.issues.specialist;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.roadservice.R;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.SampleData;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

public class IssueDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Layer droppedMarkerLayer;
    private Issue issue = SampleData.ISSUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_issue_details);

        mapView = findViewById(R.id.myIssueMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        TextView titleTextView = findViewById(R.id.myIssueTitleText);
        titleTextView.setText(issue.getTitle());

        TextView descriptionTextView = findViewById(R.id.myIssueDescText);
        descriptionTextView.setText(issue.getDescription());
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        IssueDetailsActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull final Style style) {
                initDroppedMarker(style);
            }
        });
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(issue.getLocation().getLatitude(), issue.getLocation().getLongitude()))
                .zoom(13)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 3000);
    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.blue_pin);
        assert drawable != null;
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        loadedMapStyle.addImage("dropped-icon-image", bitmap);
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id"));
        loadedMapStyle.addLayer(new SymbolLayer(DROPPED_MARKER_LAYER_ID,
                "dropped-marker-source-id").withProperties(
                iconImage("dropped-icon-image"),
                visibility(NONE),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        ));

        if (loadedMapStyle.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
            GeoJsonSource source = loadedMapStyle.getSourceAs("dropped-marker-source-id");
            if (source != null) {
                source.setGeoJson(
                        Point.fromLngLat(
                                issue.getLocation().getLongitude(),
                                issue.getLocation().getLatitude()
                        )
                );
            }
            droppedMarkerLayer = loadedMapStyle.getLayer(DROPPED_MARKER_LAYER_ID);
            if (droppedMarkerLayer != null) {
                droppedMarkerLayer.setProperties(visibility(VISIBLE));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}