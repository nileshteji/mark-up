package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DirectAction;
import android.app.Notification;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.jar.Attributes;


public class Register extends AppCompatActivity {
    FirebaseAuth Authentication;

    DatabaseReference refernce;
    ProgressBar pg;
    EditText Email, Password, CPassword, Phone, Name;
    Button Register;
    RadioGroup rg;
    RadioButton teacher, student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Authentication = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Email);
        pg = findViewById(R.id.progressBar);
        Password = findViewById(R.id.Password);
        Name = findViewById(R.id.Name);
        Phone = findViewById(R.id.phone);
        Register = findViewById(R.id.Register1);
        CPassword = findViewById(R.id.Pasword1);
        teacher = findViewById(R.id.radio_teacher);
        student = findViewById(R.id.radio_Student);
        refernce = FirebaseDatabase.getInstance().getReference("Data");

        // startActivity(new Intent(Register.this,HomePageTeacher.class));
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);

//                                            refernce.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   String a=dataSnapshot.child(Phone.getText().toString()).getKey();
//                        String b=Phone.getText().toString();
//                        Toast.makeText(Register.this, a,  Toast.LENGTH_SHORT).show()
//                     ;
//                                                    ArrayList<String> boj=new ArrayList<>();
//
//                                                    Iterable<DataSnapshot> iterable=dataSnapshot.getChildren();
//                                                    for(DataSnapshot contact :iterable){
//                                                        boj.add(contact.getKey());
//                                                    }


                    if (Password.getText().toString().length() >= 6) {
                        if (CPassword.getText().toString().equals(Password.getText().toString())) {
                            if (teacher.isChecked()) {
                                pg.setVisibility(View.VISIBLE);
                                Authentication.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            refernce.child("User").child(Phone.getText().toString()).setValue(new User(Email.getText().toString(), "Teacher", Name.getText().toString()));
                                            pg.setVisibility(View.INVISIBLE);
                                            // startActivity(new Intent(Register.this,HomePage.class));

                                        } else {
                                            Toast.makeText(Register.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                                            pg.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                            } else if (student.isChecked()) {
                                Authentication.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isComplete()) {
                                            pg.setVisibility(View.INVISIBLE);
                                            refernce.child("User").child(Phone.getText().toString()).setValue(new User(Email.getText().toString(), "Student", Name.getText().toString()));
                                            Toast.makeText(Register.this, "Done", Toast.LENGTH_SHORT).show();

                                        } else {
                                            pg.setVisibility(View.INVISIBLE);
                                            Toast.makeText(Register.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                pg.setVisibility(View.INVISIBLE);
                                Toast.makeText(Register.this, "Please Select a Category", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            pg.setVisibility(View.INVISIBLE);
                            Toast.makeText(Register.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pg.setVisibility(View.INVISIBLE);
                        Toast.makeText(Register.this, "Password Must be of 6 Characters", Toast.LENGTH_SHORT).show();
                    }


//                } else {
//                    pg.setVisibility(View.INVISIBLE);
//                    Toast.makeText(Register.this, "UserName Already Exists", Toast.LENGTH_SHORT).show();
//                }
            }


//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
////


//                if (Password.getText().toString().length() >= 6) {
//                    if (CPassword.getText().toString().equals(Password.getText().toString())) {
//                        if (teacher.isChecked()) {
//                            pg.setVisibility(View.VISIBLE);
//                            Authentication.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        refernce.child("User").child(Phone.getText().toString()).setValue(new User(Email.getText().toString(), "Teacher", Name.getText().toString()));
//                                        pg.setVisibility(View.INVISIBLE);
//                                        // startActivity(new Intent(Register.this,HomePage.class));
//
//                                    } else {
//                                        Toast.makeText(Register.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
//                                        pg.setVisibility(View.INVISIBLE);
//                                    }
//                                }
//                            });
//                        } else if (student.isChecked()) {
//                            Authentication.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isComplete()) {
//
//                                        Toast.makeText(Register.this, "Done", Toast.LENGTH_SHORT).show();
//
//                                    } else {
//                                        Toast.makeText(Register.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//
//                        } else {
//                            pg.setVisibility(View.INVISIBLE);
//                            Toast.makeText(Register.this, "Please Select a Category", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        pg.setVisibility(View.INVISIBLE);
//                        Toast.makeText(Register.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    pg.setVisibility(View.INVISIBLE);
//                    Toast.makeText(Register.this, "Password Must be of 6 Characters", Toast.LENGTH_SHORT).show();
//                }
//            }
//else {
//    Toast.makeText(Register.this, "UserName Already Exists", Toast.LENGTH_SHORT).show();
//}

        });
    }
}
//        );
//
//    }






