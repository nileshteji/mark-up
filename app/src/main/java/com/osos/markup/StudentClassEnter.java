package com.osos.markup;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.model.StudentAttendanceModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentClassEnter extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    Button currentLocation;
    Button MarkAttendance;
    TextView Date;
    TextView Time;
    TextView username;
    ProgressBar progressBar;
    EditText teacherNumber,Name,Subject,RollNumber,Batch;
    DatabaseReference databaseReference,databaseReference1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_enter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        MarkAttendance=findViewById(R.id.button2);
        databaseReference= FirebaseDatabase.getInstance().getReference("/Data/User");

        username=findViewById(R.id.username);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        teacherNumber=findViewById(R.id.TeacherNo);
        Name=findViewById(R.id.StudentNAme);
        Subject=findViewById(R.id.Subject);
        Batch=findViewById(R.id.batch);
        RollNumber=findViewById(R.id.roll);
        @SuppressLint("WrongConstant") final SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        username.setText(sharedPreferences.getString("Username","null"));
        databaseReference1=FirebaseDatabase.getInstance().getReference("/Data/User/"+sharedPreferences.getString("Username","null"));
        currentLocation = findViewById(R.id.currentLocarton1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Date=findViewById(R.id.Date);
        Time=findViewById(R.id.Time);
        Date.setText(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        Time.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));




        MarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(teacherNumber.getText().toString().trim().length()>0 && Batch.getText().toString().trim().length()>0 && progressBar.getVisibility()==View.INVISIBLE &&
                        Name.getText().toString().trim().length()>0 && Subject.getText().toString().trim().length()>0 && RollNumber.getText().toString().trim().length()>0){
                    progressBar.setVisibility(View.VISIBLE);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("/"+teacherNumber.getText().toString()+"/Attendance/"+Batch.getText().toString().toUpperCase()+"/"+Date.getText().toString()+"/"+
                                    Subject.getText().toString().toLowerCase())){

                                //TODO to create the constraint for time and location of the class
                                if(true) {


                                    databaseReference.child("/" + teacherNumber.getText().toString() + "/Attendance/" + Batch.getText().toString().toUpperCase() + "/" + Date.getText().toString() + "/" + Subject.getText().toString().toLowerCase() + "/Attendance").
                                            child(sharedPreferences.getString("Username","null")).setValue(RollNumber.getText().toString()+" "+Name.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(StudentClassEnter.this, "Attendance Added ", Toast.LENGTH_SHORT).show();
                                            } else {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(StudentClassEnter.this, "Attendance not marked...Please try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

//TODO this will be pushed to the student json file
//                                    databaseReference1.child("Attendance").child(Date.getText().toString()).child(Batch.getText().toString().toUpperCase()).



                                }
                                else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(StudentClassEnter.this, "Cannot Enter Class", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(StudentClassEnter.this, "No Class Details match...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });






                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(StudentClassEnter.this, "Please Fill the Details", Toast.LENGTH_SHORT).show();
                }


//TODO this is code is written for the fetching the data of the same

//
//           databaseReference.child("/"+teacherNumber.getText().toString()+"/Attendance/"+Batch.getText().toString().toUpperCase()+"/"+Date.getText().toString()+"/"+Subject.getText().toString().toLowerCase()+"/Attendance").
//                        addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
//                                    Log.d("Roll Number",dataSnapshot1.get);
//                                    Log.d("Name", (String) String.valueOf(dataSnapshot1.getValue()));
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });







            }
        });

        









    }


    @Override
    public void onLocationChanged(Location location) {
        LatLng current=new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("Current Locatioin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,16.0f));
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
