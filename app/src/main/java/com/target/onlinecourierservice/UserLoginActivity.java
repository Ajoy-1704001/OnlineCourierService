package com.target.onlinecourierservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.auth.User;

import java.util.concurrent.TimeUnit;

public class UserLoginActivity extends AppCompatActivity {

    EditText mobileNUmber;
    Button next;
    String mobile;
    ProgressBar progressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();


        mobileNUmber=findViewById(R.id.mobileNumber);
        next=findViewById(R.id.next);
        progressBar=findViewById(R.id.progress);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobileNUmber.getText().toString().isEmpty()){
                    mobileNUmber.setError("Invalid Mobile NUmber");
                }
                else if(mobileNUmber.getText().toString().length()==11) {
                    mobile="+88"+mobileNUmber.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    next.setEnabled(false);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile, 60, TimeUnit.SECONDS, UserLoginActivity.this, mCallbacks);
                }
            }
        });

        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(UserLoginActivity.this,"Verification Failed, Try Again",Toast.LENGTH_SHORT).show();
                next.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NonNull final String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent otpIntent=new Intent(UserLoginActivity.this,VerificationActivity.class);
                        otpIntent.putExtra("AuthCredentials",verificationId);
                        startActivity(otpIntent);
                        finish();
                    }
                },10000);

            }


        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            Intent homeIntent=new Intent(UserLoginActivity.this,MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        }
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
                                Toast.makeText(UserLoginActivity.this,"Verification Failed, Try Again",Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                        next.setEnabled(true);
                    }
                });
    }

    private void goToHome() {
        Intent homeIntent=new Intent(UserLoginActivity.this,MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}