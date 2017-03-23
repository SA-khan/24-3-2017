package com.example.saad.jspart3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class _mapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    GPSTracker myGPS;

    double mymobileLat = 0.0;
    double mymobileLong = 0.0;

    Intent myintent;
    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__map2);

        myGPS = new GPSTracker(this);
        if (myGPS.canGetLocation()) {
            mymobileLat = myGPS.getLatitude();
            mymobileLong = myGPS.getLongitude();
        } else {
            Toast.makeText(_mapActivity.this, "Go Use location!!", Toast.LENGTH_SHORT).show();
        }

        myintent = getIntent();
        latitude = myintent.getStringExtra("latitude");
        longitude = myintent.getStringExtra("longitude");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (latitude != null && longitude != null) {
            try {
                LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap.addMarker(new MarkerOptions().position(sydney).title("Office Place"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f));

                LatLng myLoc = new LatLng(mymobileLat, mymobileLong);
                mMap.addMarker(new MarkerOptions().position(myLoc).title("My Current Location"));
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);

                new GetDirection(_mapActivity.this, mMap,mymobileLat,mymobileLong, Double.parseDouble(latitude), Double.parseDouble(longitude)).execute();


            }
            catch(Exception ex){
                Log.d("Exception occured", ex.getMessage());
            }
        }
        else{
            LatLng sydney = new LatLng(24.6815,67.0099);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Unknown Latitude Loong"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}
