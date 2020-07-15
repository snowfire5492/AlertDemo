package example.hikerswatchv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private final int ACCURACY = 1;

    LocationManager lm;
    LocationListener ll;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, ACCURACY, ll);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);

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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
            Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }
        }
    }

    public void updateLocationInfo(Location location) {

        final TextView latView = findViewById(R.id.latitudeView);
        final TextView lonView = findViewById(R.id.longitudeView);
        final TextView accuracyView = findViewById(R.id.accuracyView);
        final TextView altView = findViewById(R.id.altitudeView);
        final TextView addressView = findViewById(R.id.addressValueView);
        final TextView cityView = findViewById(R.id.cityStateValueView);

        accuracyView.setText("Accuracy: " + ACCURACY + " (m)");

        latView.setText("Latitude: " + location.getLatitude());
        lonView.setText("Longitude: " + location.getLongitude());
        altView.setText("Altitude: " + location.getAltitude() + " (m)");

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            Log.i("AddressInfo", listAddresses.get(0).toString());

            if (listAddresses.get(0).getThoroughfare() != null && listAddresses.get(0).getFeatureName() != null) {
                // put street address and then city and state address

                String value = "";
                value += listAddresses.get(0).getFeatureName() + " ";
                value += listAddresses.get(0).getThoroughfare();
                addressView.setText(value);

                String city = "";
                city += listAddresses.get(0).getLocality() + ", " + listAddresses.get(0).getAdminArea() + " ";
                city += listAddresses.get(0).getPostalCode();
                cityView.setText(city);


            }else {
                // put city and state address for addressValueView and "" for other view
                String city = "";
                city += listAddresses.get(0).getLocality() + ", " + listAddresses.get(0).getAdminArea() + " ";
                city += listAddresses.get(0).getPostalCode();
                addressView.setText(city);

                cityView.setText("");
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}