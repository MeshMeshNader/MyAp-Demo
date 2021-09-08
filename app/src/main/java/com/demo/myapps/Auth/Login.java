package com.demo.myapps.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.myapps.HomeApp.Home;
import com.demo.myapps.R;
import com.demo.myapps.Utilities.CustomProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //Views
    EditText mEmail, mPassword;
    Button mLogin;
    TextView mForgetPassword , mSignUp;

    //Firebase
    FirebaseAuth mAuth;
    private CustomProgress mCustomProgress = CustomProgress.getInstance();
    //private Boolean emailAddressChecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initViews();
    }

    //Function to initiate views
    private void initViews() {
        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Edit Text
        mEmail = findViewById(R.id.login_email_et);
        mPassword = findViewById(R.id.login_password_et);

        //Text Views
        mForgetPassword = findViewById(R.id.login_forget_password);
        mForgetPassword.setOnClickListener(this);
        mSignUp = findViewById(R.id.login_signup_tv);
        mSignUp.setOnClickListener(this);

        //Button
        mLogin = findViewById(R.id.login_login_btn);
        mLogin.setOnClickListener(this);
    }

    //Click Listener Function
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_login_btn:

                if (validate())
                    loginToTheAccount();
                break;

            case R.id.login_signup_tv:
                startActivity(new Intent(Login.this, Signup.class));
                finish();
                break;

            case R.id.login_forget_password:
                startActivity(new Intent(Login.this, ForgetPassword.class));
                finish();
                break;
        }
    }

    private void loginToTheAccount() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        mCustomProgress.showProgress(this, "Logging in!!...", false);


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    VerifyEmailAddress();
                } else {
                    String messsage = task.getException().getMessage();
                    Toast.makeText(Login.this, "Error Occurred: " + messsage, Toast.LENGTH_LONG).show();
                    mCustomProgress.hideProgress();
                }
            }
        });

    }

    private void VerifyEmailAddress() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        firebaseUser.reload();
        //emailAddressChecker = firebaseUser.isEmailVerified();
       // if (emailAddressChecker) {
            mCustomProgress.hideProgress();
            Toast.makeText(Login.this, "Welcome to MyApps", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Login.this, Home.class);
            startActivity(i);
            finish();

     //   } else {
//            mCustomProgress.hideProgress();
//            Toast.makeText(this, "Please, confirm your account, check your email and try again.", Toast.LENGTH_LONG).show();
//            mAuth.signOut();
       // }


    }

    private boolean validate() {
        if (mEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your email", Toast.LENGTH_LONG).show();
            return false;
        } else if (mPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_LONG).show();
            return false;
        } else if (mPassword.getText().toString().length() < 8) {
            mPassword.setError("Password < 8");
            Toast.makeText(this, "Password should be more than 8 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}