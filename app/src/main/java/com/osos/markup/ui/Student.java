package com.osos.markup.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.osos.markup.R;

public class Student extends AppCompatActivity {
    ImageView android_class;
    CardView obj;
    TextView shared;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        android_class = findViewById(R.id.android_class);
        shared = findViewById(R.id.shared);
        obj = findViewById(R.id.materialCardView);

        obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student.this, StudentClass1.class));

            }
        });

        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("Username", MODE_APPEND);

        shared.setText(sharedPreferences.getString("Username", ""));


        android_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Student.this, StudentClassEnter.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
