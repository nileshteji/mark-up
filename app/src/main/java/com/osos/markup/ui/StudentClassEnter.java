package com.osos.markup.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.R;
import com.osos.markup.model.Details;
import com.osos.markup.model.StudentAttendanceModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentClassEnter extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    Button currentLocation;
    Button MarkAttendance;
    TextView Date;
    double lat, lang, alt;
    TextView Time;
    TextView username;
    ProgressBar progressBar;
    EditText teacherNumber, Name, Subject, RollNumber, Batch;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;
    String time;
    boolean flagTime;
    boolean locationFlag;


    double latTemp;
    Button Location;
    Location currentCoordinates;
    int a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_enter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        MarkAttendance = findViewById(R.id.button2);
        databaseReference = FirebaseDatabase.getInstance().getReference("/Data/User");
        username = findViewById(R.id.username);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        teacherNumber = findViewById(R.id.TeacherNo);
        Name = findViewById(R.id.StudentNAme);
        Subject = findViewById(R.id.Subject);
        Batch = findViewById(R.id.batch);
        Location = findViewById(R.id.currentLocarton1);
        RollNumber = findViewById(R.id.roll);

        @SuppressLint("WrongConstant") final SharedPreferences sharedPreferences = getSharedPreferences("Username", MODE_APPEND);
        username.setText(sharedPreferences.getString("Username", "null"));
        databaseReference1 = FirebaseDatabase.getInstance().getReference("/Data/User/" + sharedPreferences.getString("Username", "null"));
        currentLocation = findViewById(R.id.currentLocarton1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Date = findViewById(R.id.Date);
        Time = findViewById(R.id.Time);
        Date.setText(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        Time.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(StudentClassEnter.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(StudentClassEnter.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(StudentClassEnter.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else {
                    Log.e("Enabled", "Hi i am in else");
                    progressBar.setVisibility(View.VISIBLE);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, StudentClassEnter.this);

                }
            }
        });


        MarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (teacherNumber.getText().toString().trim().length() > 0 && Batch.getText().toString().trim().length() > 0 && progressBar.getVisibility() == View.INVISIBLE &&
                        Name.getText().toString().trim().length() > 0 && Subject.getText().toString().trim().length() > 0 && RollNumber.getText().toString().trim().length() > 0) {
                    progressBar.setVisibility(View.VISIBLE);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(teacherNumber.getText().toString() + "/Attendance/" + Batch.getText().toString().toUpperCase() + "/" + Date.getText().toString() + "/" +
                                    Subject.getText().toString().toLowerCase())) {
                                process();
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(StudentClassEnter.this, "No Class Details match...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(StudentClassEnter.this, "Please Fill the Details", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public void process() {
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("Username", MODE_APPEND);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("/Data/User").child("/" + teacherNumber.getText().toString() + "/Attendance/" + Batch.getText().toString().toUpperCase() + "/" + Date.getText().toString() + "/" + Subject.getText().toString().toLowerCase() + "/Details");
        if (checkTime()) {
            if (checkLocation()) {
                databaseReference.child("/" + teacherNumber.getText().toString() + "/Attendance/" + Batch.getText().toString().toUpperCase() + "/" + Date.getText().toString() + "/" + Subject.getText().toString().toLowerCase() + "/Attendance").
                        child(sharedPreferences.getString("Username", "null")).setValue(RollNumber.getText().toString() + " " + Name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            studentAttendance studentAttendance = new studentAttendance();
                            studentAttendance.execute();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(StudentClassEnter.this, "Attendance not marked...Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Please Try again ! Cannot Enter Class", Toast.LENGTH_SHORT).show();
            }


        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(StudentClassEnter.this, "Please Try again!cannot enter class", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkTime() {
        final String TAG = "tag";

        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Details model = dataSnapshot.getValue(Details.class);
                time = model.getTime();
                String currentTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                String[] timeArray = new String[2];
                timeArray = time.split(":");
                String[] currentTimeArray = new String[2];
                currentTimeArray = currentTime.split(":");
                int a = Integer.valueOf(currentTimeArray[1]);
                int e = Integer.valueOf(timeArray[1]);
                int b = Integer.valueOf(timeArray[1]) + 15;
                int c = Integer.valueOf(timeArray[0]);
                int d = Integer.valueOf(currentTimeArray[0]);
                Log.d(TAG, "onDataChange: " + e + " " + a + " " + b);
                Log.d(TAG, "onDataChange: " + c + d);
                if (e <= a && a <= b) {
                    if (c == d) {
                        flagTime = true;
                    } else {
                        flagTime = false;
                    }
                } else {
                    flagTime = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return flagTime;
    }


    public boolean checkLocation() {
        final String TAG = "LOcation";


        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Details details = dataSnapshot.getValue(Details.class);
                Log.d(TAG, "onDataChange: " + details.getLat() + " " + details.getLong() + " " + lat + " " + lang);
                Log.d(TAG, "onDataChange: " + distance(details.getLat(), details.getLong(), lat, lang, 'K') * 1000);
                Location location = new Location(LocationManager.GPS_PROVIDER);
                location.setLatitude(details.getLat());
                location.setLongitude(details.getLong());
                Log.d(TAG, "HI Duiisat" + location.distanceTo(currentCoordinates));
                float locationFloat = location.distanceTo(currentCoordinates);
                float constraint = 7f;
                int a = Float.compare(locationFloat, constraint);

                if (a < 0) {
                    locationFlag = true;
                } else {
                    locationFlag = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return locationFlag;

    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void onLocationChanged(Location location) {
        String TAG = "Final";
        lang = location.getLongitude();
        lat = location.getLatitude();
        alt = location.getAltitude();
        currentCoordinates = location;
        Log.d(TAG, "onLocationChanged: " + lang + " " + lat);
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("Current Locatioin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16.0f));
        progressBar.setVisibility(View.INVISIBLE);


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
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(StudentClassEnter.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(StudentClassEnter.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(StudentClassEnter.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            Log.e("Enabled", "Hi i am in else");

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, StudentClassEnter.this);

        }

    }

    class studentAttendance extends AsyncTask<Void, Void, Void> {

        @SuppressLint({"WrongThread", "WrongConstant"})
        @Override
        protected Void doInBackground(Void... voids) {
            //TODO this is code is written for the fetching the data of the same
            databaseReference1.child("/Attendance").child(Subject.getText().toString().toLowerCase()).
                    child(Date.getText().toString() + " " + getSharedPreferences("Username", MODE_APPEND).getString("Username", "null")).
                    setValue(new StudentAttendanceModel(
                            Batch.getText().toString(),
                            Time.getText().toString(),
                            Subject.getText().toString(),
                            teacherNumber.getText().toString(),
                            Name.getText().toString(),
                            RollNumber.getText().toString(),
                            Date.getText().toString()));


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(StudentClassEnter.this, "Attendance Marked", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
