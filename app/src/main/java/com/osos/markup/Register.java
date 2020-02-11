package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
FirebaseAuth Authentication;

DatabaseReference refernce;
EditText Email,Password,CPassword;
Button Register;
RadioGroup rg;
RadioButton teacher,student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Authentication = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Register = findViewById(R.id.Register1);
        CPassword = findViewById(R.id.Pasword1);
        teacher=findViewById(R.id.radio_teacher);
        student=findViewById(R.id.radio_Student);
     refernce=FirebaseDatabase.getInstance().getReference("Data");
        Register.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        if(Password.getText().toString().length()>=6){
            if (CPassword.getText().toString().equals(Password.getText().toString())) {
                if (teacher.isChecked()) {

                    Authentication.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //  refernce.child("Student").child(Email.getText().toString());

                                Toast.makeText(Register.this, "Done", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Register.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (student.isChecked()) {
                    Authentication.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isComplete()) {

                                Toast.makeText(Register.this, "Done", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Register.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(Register.this, "Please Select a Category", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Register.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Register.this, "Password Must be of 6 Characters", Toast.LENGTH_SHORT).show();
        }
    }
});


    }


}
