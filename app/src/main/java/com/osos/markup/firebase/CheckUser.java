package com.osos.markup.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckUser {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/Data/User");
    boolean user;


}