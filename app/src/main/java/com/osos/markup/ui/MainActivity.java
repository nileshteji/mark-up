package com.osos.markup.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.markup.R;
import com.osos.markup.model.User;

public class MainActivity extends AppCompatActivity {

    Button login, signUp;
    TextView markup;
    FirebaseAuth Auth;
    ImageView interior;
    ConstraintLayout layout;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        markup = findViewById(R.id.textView);
        signUp = findViewById(R.id.Register);
        layout = findViewById(R.id.layout);
        interior = findViewById(R.id.image);
        Auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("/Data/User");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Animation slideup= AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_up);
//                markup.startAnimation(slideup);


                if (Auth.getCurrentUser() == null) {
                    layout.setBackgroundColor(Color.WHITE);
                    login.setVisibility(View.VISIBLE);
                    signUp.setVisibility(View.VISIBLE);
                    interior.setVisibility(View.VISIBLE);
                    markup.setVisibility(View.INVISIBLE);
                } else {
                    Log.d("TAG", Auth.getCurrentUser().getEmail());


                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("Username", MODE_APPEND);
                                User user = dataSnapshot.child(sharedPreferences.getString("Username", "")).getValue(User.class);
                                if (user.getCategory().equals("Student")) {
                                    startActivity(new Intent(MainActivity.this, Student.class));
                                } else {
                                    startActivity(new Intent(MainActivity.this, Teacher.class));
                                }

                            } catch (Exception e) {

                                Toast.makeText(MainActivity.this, "You can only sign in with the phone from which you signed in", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        }, 2000);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
