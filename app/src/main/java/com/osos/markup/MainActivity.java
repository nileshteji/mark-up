package com.osos.markup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

Button login,signUp;
TextView markup;
FirebaseAuth Auth;
ImageView interior;
ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.login);
        markup=findViewById(R.id.textView);
        signUp=findViewById(R.id.Register);
        layout=findViewById(R.id.layout);
        interior=findViewById(R.id.image);
        Auth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Animation slideup= AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_up);
//                markup.startAnimation(slideup);
                layout.setBackgroundColor(Color.WHITE);
                login.setVisibility(View.VISIBLE);
                signUp.setVisibility(View.VISIBLE);
                interior.setVisibility(View.VISIBLE);
            markup.setVisibility(View.INVISIBLE);
          //      startActivity(new Intent(MainActivity.this,Register.class));
                //Toast.makeText(MainActivity.this, "Hi user", Toast.LENGTH_SHORT).show();
            }
        },2000);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });
    }
}
