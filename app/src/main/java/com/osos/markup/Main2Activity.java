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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.Adapters.DateAdapter;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<String> data;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String a1;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar=findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setTitleMargin(50,0,0,0);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        progressBar=findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.recyclerView2);
        Intent recieve=getIntent();
        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);
        a =sharedPreferences.getString("Username","");
       a1=recieve.getStringExtra("data");
        getSupportActionBar().setTitle(a1);
        databaseReference=FirebaseDatabase.getInstance()
                .getReference("/Data/User"+"/"+a+"/Attendance/"+a1);
        data=new ArrayList<>();



      databaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              data=new ArrayList<>();
              for(DataSnapshot d:dataSnapshot.getChildren()){
                  data.add(d.getKey());
              }

              DateAdapter dateAdapter=new DateAdapter(Main2Activity.this,data,Main2Activity.this);
              LinearLayoutManager linearLayoutManager =new LinearLayoutManager(Main2Activity.this);
              linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
              recyclerView.setLayoutManager(linearLayoutManager);
              recyclerView.setAdapter(dateAdapter);




              progressBar.setVisibility(View.INVISIBLE);

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
              progressBar.setVisibility(View.INVISIBLE);

          }
      });










    }

    public void onClick(String a){
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
        //data
        //date
        Intent intent=new Intent(Main2Activity.this,SubjectActivity.class);
        intent.putExtra("date",a);
        intent.putExtra("batch",a1);
        startActivity(intent);





    }

}
