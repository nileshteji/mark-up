package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
EditText Username,Password;
Button Login;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth.getInstance();
        Username=findViewById(R.id.editText);
        Password=findViewById(R.id.editText2);

        Login=findViewById(R.id.button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mAuth.signInWithEmailAndPassword(Username.getText().toString(),Password.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             startActivity();
                         }
                 }
             });
            }
        });
    }
}
