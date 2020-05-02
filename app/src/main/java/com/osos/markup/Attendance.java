package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.osos.markup.Adapters.ClassAdapter;

import java.util.ArrayList;

public class Attendance extends AppCompatActivity {

    DatabaseReference mReference;
    ArrayList<String> arrayList;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        toolbar=findViewById(R.id.toolbar2);
        recyclerView=findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Classes");
        toolbar.setTitleMarginStart(50);
        progressBar=findViewById(R.id.progressBar3);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Attendance.this,Teacher.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        progressBar.setVisibility(View.VISIBLE);

        mReference=FirebaseDatabase.getInstance().getReference("/Data/User");
      arrayList=new ArrayList<>();
        mReference.child(getSharedPreferences("Username",MODE_APPEND).getString("Username","")).child("Attendance").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList=new ArrayList<>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                   arrayList.add(dataSnapshot1.getKey());
                }



                ClassAdapter obj=new ClassAdapter(Attendance.this,arrayList,Attendance.this);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Attendance.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setAdapter(obj);
                recyclerView.setLayoutManager(linearLayoutManager);
                progressBar.setVisibility(View.INVISIBLE);










            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Attendance.this, "In Error", Toast.LENGTH_SHORT).show();

            }
        });


        //Log.d("Finieshed","Nilesh");









    }

    public void OnClick(String a){
        Intent intent=new Intent(Attendance.this,Main2Activity.class);
        intent.putExtra("data",a);
        startActivity(intent);

    }



}
