package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity {
FirebaseAuth Authentication;
EditText Email,Password,CPassword;
Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Authentication = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Register = findViewById(R.id.Register);
        CPassword = findViewById(R.id.Pasword1);

Register.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        if (Email.getText().toString().equals(Password.getText().toString())) {

        Authentication.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                  Toast.makeText(Register.this, "Complete Registration", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Register.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();

            }
        });
    }
    }
});


    }


}
