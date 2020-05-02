package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.data.DataBuffer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student extends AppCompatActivity {
ImageView android_class;
CardView obj;
TextView shared;
DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        android_class=findViewById(R.id.android_class);
        shared=findViewById(R.id.shared);
        obj=findViewById(R.id.materialCardView);
        mDatabase= FirebaseDatabase.getInstance().getReference("/Data/User/9356364121/Attendance/OSSB2/02-05-2020/microprocessor/Attendance");


//TODO Attendance showing for each teacher
//
// mDatabase.addValueEventListener(new ValueEventListener() {
//     @Override
//     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//         for(DataSnapshot temp:dataSnapshot.getChildren()){
//             Log.d("TAG",temp.getKey()+" "+temp.getValue());
//         }
//
//     }
//
//     @Override
//     public void onCancelled(@NonNull DatabaseError databaseError) {
//
//     }
// });


        obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student.this,StudentClass1.class));

            }
        });

        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences=getSharedPreferences("Username",MODE_APPEND);

        shared.setText(sharedPreferences.getString("Username",""));



        android_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student.this,StudentClassEnter.class));
            }
        });
    }
}
