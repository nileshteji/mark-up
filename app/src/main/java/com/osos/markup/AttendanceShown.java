package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.Adapters.AttendanceDisplayAdapter;
import com.osos.markup.model.StudentAttendanceModel;

import java.util.ArrayList;

public class AttendanceShown extends AppCompatActivity {
String a;
DatabaseReference mDatabaseReference;
ArrayList<StudentAttendanceModel> list;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_shown);
        toolbar=findViewById(R.id.toolbar2);
        recyclerView=findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);

        toolbar.setTitleMarginStart(50);
        progressBar=findViewById(R.id.progressBar3);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AttendanceShown.super.onBackPressed();
            }
        });
        list=new ArrayList<>();
        toolbar.setTitleTextColor(Color.WHITE);
        progressBar.setVisibility(View.VISIBLE);
        a=getIntent().getStringExtra("subject");
        getSupportActionBar().setTitle(a);
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("/Data/User/"+sharedPreferences.getString("Username","null")+"/Attendance/"+a.toLowerCase());



        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for(DataSnapshot temp:dataSnapshot.getChildren()){
                    list.add(temp.getValue(StudentAttendanceModel.class));
                }


                AttendanceDisplayAdapter attendanceDisplayAdapter =new AttendanceDisplayAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(attendanceDisplayAdapter);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                progressBar.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }
}
