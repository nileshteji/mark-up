package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osos.markup.model.Details;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClassEnter extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
   Button currentLocation,Add;
    TextView lat,lang,alt;
    ProgressBar boj;
    DatePicker datePicker;
    TimePicker timePicker;
    TextInputEditText CourseCode,ClassName;
   TextView username;
   TextView time;
   TextView date;
    double alt1;
    double lang1;
    double lat1;
   DatabaseReference dbRefernce;
   Marker marker;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_enter);

        Add=findViewById(R.id.button2);
        ClassName=findViewById(R.id.textInputEditText);
        CourseCode=findViewById(R.id.textInputEditText2);
        date=findViewById(R.id.date);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        boj=findViewById(R.id.progressBar4);
        dbRefernce= FirebaseDatabase.getInstance().getReference("/Data/User") ;

        time=findViewById(R.id.editText5);
        username=findViewById(R.id.username);
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        String a =sharedPreferences.getString("Username","");

        username.setText(a);


       // datePicker=findViewById(R.id.datePicker);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ClassEnter.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                    date.setText((checkDigit(dayOfMonth )+ "-" +checkDigit(monthOfYear + 1) + "-" + year));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
              int  mHour = c.get(Calendar.HOUR_OF_DAY);
               int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ClassEnter.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        currentLocation = findViewById(R.id.currentLocarton1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       currentLocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (ActivityCompat.checkSelfPermission(ClassEnter.this,
                       Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                       ActivityCompat.checkSelfPermission(ClassEnter.this,
                               Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                   ActivityCompat.requestPermissions(ClassEnter.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
               }
               else {
                   Log.e("Enabled","Hi i am in else");
                   boj.setVisibility(View.VISIBLE);
                  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,ClassEnter.this);

               }
           }
       });

Add.setOnClickListener(new View.OnClickListener(){

               @Override
                public void onClick(View v) {
                   boj.setVisibility(View.VISIBLE);

            dbRefernce.child(username.getText().toString())
                    .child("Attendance").child(ClassName.getText().toString().toUpperCase()).child(date.getText().toString())
                    .child(CourseCode.getText().toString().toLowerCase()).child("Details").setValue(new Details(alt1,
                    lat1,lang1 ,time.getText().toString())).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            boj.setVisibility(View.INVISIBLE);
                            Toast.makeText(ClassEnter.this, "Class Inserted", Toast.LENGTH_SHORT).show();

                        }
                    });
            Log.d("Lag", String.valueOf(Float.valueOf(lang.getText().toString())+0.00000001000));

//
//            String  Alt;
//            String Lat;
//            String Long;
//            String time;

        }


        });






    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


    // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(ClassEnter.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ClassEnter.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ClassEnter.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        else {
            Log.e("Enabled","Hi i am in else");
            boj.setVisibility(View.VISIBLE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,ClassEnter.this);

        }
}
    @Override
    public void onLocationChanged(Location location) {
        lat1=location.getLatitude();
        lang1=location.getLongitude();
        alt1=location.getAltitude();
        Log.d("TAG", "onLocationChanged: "+lat1);

        lat=findViewById(R.id.lat);
        lang=findViewById(R.id.lang);
        alt=findViewById(R.id.alt);
        boj.setVisibility(View.INVISIBLE);
      lat.setText(String.valueOf(location.getLatitude()));
      lang.setText(String.valueOf(location.getLongitude()));
      alt.setText(String.valueOf(location.getAltitude()));
      LatLng current=new LatLng(location.getLatitude(),location.getLongitude());
      mMap.addMarker(new MarkerOptions().position(current).title("Current Location"));
      marker=mMap.addMarker(new MarkerOptions().position(current).title("Current Location"));
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
}
