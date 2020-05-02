package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.model.StudentAttendanceModel;

import java.util.ArrayList;

public class AttendanceShown extends AppCompatActivity {
String a;
DatabaseReference mDatabaseReference;
ArrayList<StudentAttendanceModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_shown);
        a=getIntent().getStringExtra("subject");
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("/Data/User/"+sharedPreferences.getString("Username","null")+"/Attendance/"+a);



        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot temp:dataSnapshot.getChildren()){
                    list.add(temp.getValue(StudentAttendanceModel.class));
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }
}
