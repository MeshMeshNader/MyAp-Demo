package com.demo.myapps.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.myapps.R;
import com.demo.myapps.Utilities.CustomProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener{

    //Views
    EditText mEmail;
    Button mReset, mSignUp;

    //Fire base
    FirebaseAuth mAuth;
    private CustomProgress mCustomProgress = CustomProgress.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initViews();

    }

    private void initViews() {
        //Views
        mEmail = findViewById(R.id.forget_password_email_et);
        mReset = findViewById(R.id.forget_password_reset_btn);
        mReset.setOnClickListener(this);
        mSignUp = findViewById(R.id.forget_password_signup_btn);
        mSignUp.setOnClickListener(this);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.forget_password_reset_btn:
                if (mEmail.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your email address", Toast.LENGTH_LONG).show();
                } else {
                    mCustomProgress.showProgress(this, "Please Wait!..." , true);
                    resetPassword();
                }
                break;

            case R.id.forget_password_signup_btn:
                startActivity(new Intent(ForgetPassword.this, Signup.class));
                finish();
                break;
        }
    }

    private void resetPassword() {

        String userEmail = mEmail.getText().toString();

        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mCustomProgress.hideProgress();
                    Toast.makeText(ForgetPassword.this, "Please Check your Email to reset your password", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetPassword.this, Login.class));
                    finish();
                } else {
                    String message = task.getException().toString();
                    Toast.makeText(ForgetPassword.this, "Error Occurrd : " + message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}