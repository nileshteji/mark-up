package com.osos.markup;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class PhoneAuth  {
FirebaseAuth mAuth=FirebaseAuth.getInstance();
String Phone;
Context activity;
boolean flag=false;

PhoneAuth(String Phone,Context activity){
    this.Phone=Phone;
    this.activity=activity;
}

public void getAauth(){

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }
    };

    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+Phone,180, TimeUnit.SECONDS, (Activity) activity,callbacks);


}



public void signWithPhone(PhoneAuthCredential phone){
    mAuth.signInWithCredential(phone).addOnCompleteListener((Activity) activity, new OnCompleteListener<AuthResult>() {


        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                flag=true;
                Toast.makeText(activity, "Phone Verified", Toast.LENGTH_SHORT).show();

            }
            else{
                flag=false;
            }

        }



    });
}


public boolean getResult(){
    return (flag)?true:false;
}





}
