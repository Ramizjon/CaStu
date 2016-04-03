package ramiz.com.castu.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ramiz.com.castu.R;

public class LocationPickerMapActivity extends FragmentActivity
        implements OnMapClickListener, OnMapLongClickListener {

    Button approveLocationButton;
    Marker selectedMarker;
    private static final double DEFAULT_LATITUDE = 46.4953023;
    private static final double DEFAULT_LONGITUDE = 30.8023548;
    LatLng defaultLocation = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    private GoogleMap mMap;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_picker_map_activity);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.select_from_map_fragment);
        approveLocationButton = (Button) findViewById(R.id.approveLocationButton);
        approveLocationButton.setOnClickListener(addCurrentLocationButtonOnClickListener);
        mMap = fragment.getMap();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(defaultLocation, 10);
        mMap.animateCamera(cameraUpdate);
        setUpMap();
    }

    View.OnClickListener addCurrentLocationButtonOnClickListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMarker != null) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("latitude", String.valueOf(selectedMarker.getPosition().latitude));
                    returnIntent.putExtra("longitude", String.valueOf(selectedMarker.getPosition().longitude));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    Toast.makeText(LocationPickerMapActivity.this, "Местоположение не выбрано", Toast.LENGTH_SHORT).show();
                }
            }
        };


    private void setUpMap() {
        if (mMap != null) {
            mMap.setOnMapClickListener(this);
            mMap.setOnMapLongClickListener(this);
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        if (selectedMarker == null) {
            selectedMarker = mMap.addMarker(new MarkerOptions().position(point).title("Тупизм"));
            selectedMarker.setDraggable(true);
            approveLocationButton.setText("Подтвердить местоположение");
            approveLocationButton.setBackgroundColor(Color.parseColor("#10CC8A"));
        } else {
            selectedMarker.setPosition(point);
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {

        //mTapTextView.setText("long pressed, point=" + point);
    }
}