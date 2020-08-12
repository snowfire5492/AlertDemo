package com.example.listofplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class AddAPlaceMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;

    public void centerMapOnLocation(Location location, String title) {

        if (location != null) {
            mMap.clear();
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_place_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Intent intent = getIntent();
//        Toast.makeText(this, "" + intent.getIntExtra("placeNumber", 0), Toast.LENGTH_SHORT ).show();

        mMap.setOnMapLongClickListener(this);


        if(intent.getIntExtra("placeNumber", -1) == 0) {

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
//              Log.i("LOCATION", location.toString());


                    // zoom in on user location
                    centerMapOnLocation(location, "user");



                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {


                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastLocation, "lastLocation");



            }
        }else {
            {

                Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
                placeLocation.setLatitude(MainActivity.latlng.get(intent.getIntExtra("placeNumber", 0)).latitude);
                placeLocation.setLongitude(MainActivity.latlng.get(intent.getIntExtra("placeNumber", 0)).longitude);

                centerMapOnLocation(placeLocation, MainActivity.places.get(intent.getIntExtra("placeNumber", 0)));
            }
        }



    }
    @Override
    public void onMapLongClick(LatLng latLng) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";

        try {
            List<Address> list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (list != null && list.size() > 0) {
                Log.i("ADDRESS", list.get(0).toString());

                if (list.get(0).getThoroughfare() != null) {
                    address = list.get(0).getFeatureName() + " " + list.get(0).getThoroughfare();
                }else if (list.get(0).getThoroughfare() == null) {
                    address = "[" + latLng.latitude + ", " + latLng.longitude + "]";
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        mMap.addMarker(new MarkerOptions().position(latLng).title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        MainActivity.places.add(address);
        MainActivity.latlng.add(latLng);

        MainActivity.arrayAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Location Saved!", Toast.LENGTH_SHORT).show();

    }

}