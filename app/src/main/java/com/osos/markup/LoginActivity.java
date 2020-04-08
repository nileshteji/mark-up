package com.osos.markup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.osos.markup.model.User;

public class LoginActivity extends AppCompatActivity {
    EditText Email, Password, Phone;
    Button Login;
    FirebaseAuth mAuth;
    ProgressBar pg;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Data").child("User");

    @Override
    protected void onStart() {
        super.onStart();
        Login.setClickable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance().getInstance();
        Email = findViewById(R.id.editText);
        Phone = findViewById(R.id.editText3);
        pg=findViewById(R.id.progressBar2);
        Password = findViewById(R.id.editText2);

        Login = findViewById(R.id.button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);
                Login.setClickable(false);
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(Phone.getText().toString()).getValue(User.class) != null) {
                                            final User obj = dataSnapshot.child(Phone.getText().toString()).getValue(User.class);
                                            mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).
                                                    addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {

                                                                if ((obj.getCategory().toString()).equals("Teacher")) {
//                                       Login.setClickable(false);
                                                                    pg.setVisibility(View.INVISIBLE);
                                                                    Intent intent = new Intent(LoginActivity.this, Teacher.class);
                                                                    SharedPreferences sharedPreferences = getSharedPreferences("Username", MODE_PRIVATE);
                                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                    editor.putString("Username", Phone.getText().toString());
                                                                    editor.commit();
                                                                    startActivity(intent);
                                                                } else if (obj.getCategory().toString().equals("Student ")) {
                                                                    pg.setVisibility(View.INVISIBLE);
                                                                    Toast.makeText(LoginActivity.this, "Working on this feature", Toast.LENGTH_SHORT).show();

                                                                }


                                                            } else {
                                                                pg.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(LoginActivity.this, "Please Try Again Later", Toast.LENGTH_SHORT).show();
                                                                Login.setClickable(true);
                                                            }


                                                        }
                                                    });
                                        } else {

                                            pg.setVisibility(View.INVISIBLE);
                                            final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                            alert.setTitle("No User Exists");
                                            alert.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    startActivity(new Intent(LoginActivity.this, Register.class));
                                                }
                                            });

                                            alert.setCancelable(true);
                                            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialog) {
                                                    dialog.cancel();
                                                }
                                            });
                                            Login.setClickable(true);
                                            alert.show();
//
                                        }
//


                                }

                                    @Override
                                    public void onCancelled (@NonNull DatabaseError databaseError){

                                    }


                    ;
                        });
            }
        });
    }
}


