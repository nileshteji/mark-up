package com.osos.markup.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.Adapters.StudentSubjectsAdapter;
import com.osos.markup.R;

import java.util.ArrayList;

public class StudentClass1 extends AppCompatActivity {

    DatabaseReference mReference;
    ArrayList<String> arrayList;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<String> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class1);
        toolbar = findViewById(R.id.toolbar2);
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Subjects");
        toolbar.setTitleMarginStart(50);
        subjects = new ArrayList<>();
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("Username", MODE_APPEND);
        mReference = FirebaseDatabase.getInstance().getReference("/Data/User/" + sharedPreferences.getString("Username", "null") + "/Attendance");
        progressBar = findViewById(R.id.progressBar3);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentClass1.this, Student.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);


        mReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjects = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    subjects.add(dataSnapshot1.getKey().toUpperCase());


                }
                StudentSubjectsAdapter studentSubjectsAdapter = new StudentSubjectsAdapter(StudentClass1.this, subjects, StudentClass1.this);
                recyclerView.setAdapter(studentSubjectsAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void onAttendance(String a) {
        Intent intent = new Intent(StudentClass1.this, AttendanceShown.class);
        intent.putExtra("subject", a);
        startActivity(intent);
    }
}
