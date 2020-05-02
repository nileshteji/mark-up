package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.Adapters.TeacherAttendanceShowAdapter;

import java.util.ArrayList;

public class TeacherAttendanceShow extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String batch,date,Subject;
    DatabaseReference databaseReference;
    ArrayList<String> list;
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_show);
        toolbar=findViewById(R.id.toolbar2);
        recyclerView=findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(50);
        progressBar=findViewById(R.id.progressBar3);
        warning=findViewById(R.id.textView3);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TeacherAttendanceShow.super.onBackPressed();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
     batch=getIntent().getStringExtra("batch");
     date=getIntent().getStringExtra("date");
     Subject=getIntent().getStringExtra("subject");


        getSupportActionBar().setTitle(batch+" "+date+" "+Subject);
        progressBar.setVisibility(View.VISIBLE);
        //mDatabase= FirebaseDatabase.getInstance().getReference("/Data/User/9356364121/Attendance/OSSB2/02-05-2020/microprocessor/Attendance");
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        String userTemp=sharedPreferences.getString("Username","null");
      

        databaseReference = FirebaseDatabase.getInstance().getReference("/Data/User/" + userTemp + "/Attendance/" + batch + "/" + date + "/" + Subject+"/Attendance");
        databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list=new ArrayList<>();
                    for (DataSnapshot temp : dataSnapshot.getChildren()) {
                        Log.d("TAG", temp.getKey() + " " + temp.getValue());

                        list.add(temp.getKey() + " " + temp.getValue());
                    }
                    if(list.size()==0){
                        warning.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        TeacherAttendanceShowAdapter teacherAttendanceShowAdapter = new TeacherAttendanceShowAdapter(getApplicationContext(), list);
                        recyclerView.setAdapter(teacherAttendanceShowAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        progressBar.setVisibility(View.INVISIBLE);
                        warning.setVisibility(View.INVISIBLE);
                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

















    }
}
