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
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.Adapters.SubjectAdapter;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String a;
    String batch;
    String date;
    ArrayList<String> arrayList;
    RecyclerView subjectRecyler;
    Toolbar toolbar;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        String aTemp =sharedPreferences.getString("Username","");
        toolbar=findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        subjectRecyler=findViewById(R.id.subject_recycler_view);
        toolbar.setTitleMarginStart(50);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SubjectActivity.super.onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);














        date=getIntent().getStringExtra("date");
      batch=getIntent().getStringExtra("batch");
      arrayList=new ArrayList<>();
        getSupportActionBar().setTitle(batch+" "+date);
      databaseReference= FirebaseDatabase.getInstance().
                getReference("/Data/User/"+aTemp+"/Attendance/"+batch+"/"+date);
        Log.d("KEY1",aTemp);
        Log.d("KEY2",batch);
        Log.d("KEY3",date);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    arrayList.add(dataSnapshot1.getKey());

                }
                SubjectAdapter subjectAdapter=new SubjectAdapter(getApplicationContext(),
                       arrayList,SubjectActivity.this);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                subjectRecyler.setAdapter(subjectAdapter);
                subjectRecyler.setLayoutManager(linearLayoutManager);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void onClick(String a){

        Intent intent=new Intent(SubjectActivity.this,TeacherAttendanceShow.class);
        intent.putExtra("batch",batch);
        intent.putExtra("date",date);
        intent.putExtra("subject",a);
        startActivity(intent);



    }
}
