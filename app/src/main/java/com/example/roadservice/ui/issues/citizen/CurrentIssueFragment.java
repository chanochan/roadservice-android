package com.example.roadservice.ui.issues.citizen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.models.County;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.Province;
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

public class CurrentIssueFragment extends Fragment implements OnMapReadyCallback {
    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private static final String TAG = "CurrentIssueFragment";
    TextView descriptionTextView, titleTextView, countyTextView, provinceTextView;
    private Issue issue;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Layer droppedMarkerLayer;

    public CurrentIssueFragment() {
        // Required empty public constructor
    }

    public static CurrentIssueFragment newInstance(Issue issue) {
        CurrentIssueFragment fragment = new CurrentIssueFragment();
        fragment.issue = issue;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_issue, container, false);

        Log.d("SHIT", "Running on create view");
        titleTextView = view.findViewById(R.id.myIssueTitleText);
        descriptionTextView = view.findViewById(R.id.myIssueDescText);
        provinceTextView = view.findViewById(R.id.provinceTextView);
        countyTextView = view.findViewById(R.id.countyTextView);

        mapView = view.findViewById(R.id.myIssueMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        showData();

        return view;
    }

    public void setData(Issue issue) {
        this.issue = issue;
        showData();
    }

    private void showData() {
        if (issue == null) return;
        if (titleTextView != null) {
            titleTextView.setText(issue.getTitle());
            descriptionTextView.setText(issue.getDescription());

            Log.d(TAG, Integer.toString(issue.getCounty()));
            County county = Database.getCounty(issue.getCounty());
            countyTextView.setText(county.getName());

            Province province = Database.getProvince(county.getProvinceId());
            provinceTextView.setText(province.getName());
        }
        if (mapboxMap != null) {
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(issue.getLocation().getLatitude(), issue.getLocation().getLongitude()))
                    .zoom(13)
                    .build();
            System.out.println(issue.getLocation().toString());
            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 2000);
        }
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, this::initDroppedMarker);
        showData();
    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.blue_pin);
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
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}