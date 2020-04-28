package com.osos.markup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentClassEnter extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    Button currentLocation;
    TextView lat,lang,alt;
    Button EnterAtt;
    TextView Date;
    TextView Time;
    TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_enter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        username=findViewById(R.id.username);
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        username.setText(sharedPreferences.getString("Username","null"));
        currentLocation = findViewById(R.id.currentLocarton1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Date=findViewById(R.id.Date);
        Time=findViewById(R.id.Time);
        Date.setText(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        Time.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
        









    }


    @Override
    public void onLocationChanged(Location location) {
        LatLng current=new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("Current Locatioin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,16.0f));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        if (ActivityCompat.checkSelfPermission(StudentClassEnter.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(StudentClassEnter.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(StudentClassEnter.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        else {
            Log.e("Enabled","Hi i am in else");

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,StudentClassEnter.this);

        }

    }



}
