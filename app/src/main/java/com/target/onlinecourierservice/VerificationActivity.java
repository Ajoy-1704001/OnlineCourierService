package com.target.onlinecourierservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Button next;
    EditText otp;
    ProgressBar progressBar;
    String mAuthVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        next=findViewById(R.id.next);
        otp=findViewById(R.id.otp);
        progressBar=findViewById(R.id.progress);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        mAuthVerificationId=getIntent().getStringExtra("AuthCredentials");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpCode=otp.getText().toString();
                if(otpCode.isEmpty()){
                    otp.setError("Enter a Valid OTP");
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    next.setEnabled(false);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otpCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goToHome();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                otp.setError("Invalid OTP");
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                        next.setEnabled(true);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            goToHome();
        }
    }

    public void goToHome() {
        Intent homeIntent=new Intent(VerificationActivity.this,MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}