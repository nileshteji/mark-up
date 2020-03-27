package com.osos.markup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class Teacher extends AppCompatActivity {
DrawerLayout drawerLayout;
DrawerLayout boj;
ActionBarDrawerToggle drawerToggle;
NavigationView navigationView;
ImageView attendClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        attendClass=findViewById(R.id.android_class);


        attendClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Teacher.this,StartClass.class));
            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
