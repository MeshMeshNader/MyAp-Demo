package com.demo.myapps.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.myapps.Utilities.AESCrypt;
import com.demo.myapps.Utilities.CustomProgress;
import com.demo.myapps.R;
import com.demo.myapps.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity implements View.OnClickListener {


    //View
    Button mSignUp;
    EditText mFName, mLName, mEmail, mPhoneNumber, mNationalID, mPassword, mConfirmPassword;
    TextView mHaveAnAccount;

    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference UsersRef;

    //ProgressBar
    CustomProgress mCustomProgress = CustomProgress.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
    }

    private void initViews() {
        //Button
        mSignUp = findViewById(R.id.signup_signup_btn);
        mSignUp.setOnClickListener(this);
        //Text View
        mHaveAnAccount = findViewById(R.id.signup_already_have_acc);
        mHaveAnAccount.setOnClickListener(this);
        //Edit Text
        mFName = findViewById(R.id.signup_first_name_et);
        mLName = findViewById(R.id.signup_last_name_et);
        mEmail = findViewById(R.id.signup_email_et);
        mPhoneNumber = findViewById(R.id.signup_phone_et);
        mNationalID = findViewById(R.id.signup_national_id_et);
        mPassword = findViewById(R.id.signup_password_et);
        mConfirmPassword = findViewById(R.id.signup_confirm_password_et);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference("Users");
    }


    //Click Listener Function
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_signup_btn:
                if (validate())
                    createAccount();
                break;

            case R.id.signup_already_have_acc:
                startActivity(new Intent(Signup.this, Login.class));
                finish();
                break;
        }
    }


    //Function to create account with firebase
    private void createAccount() {
        mCustomProgress.showProgress(this, "Please Wiat!!...", false);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //sendVerificationMessage();
                            saveAccountData();
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText(Signup.this, "Error Occurred: " + message, Toast.LENGTH_LONG).show();
                            mCustomProgress.hideProgress();
                        }

                    }
                });


    }

    private void saveAccountData() {
        String password;
        try {
            password = AESCrypt.encrypt(mPassword.getText().toString());
        } catch (Exception e) {
            Log.e("MyApps-Signup", "saveAccountUserData: " + e.getMessage());
            password = mPassword.getText().toString();
        }

        String currentUserID = mAuth.getCurrentUser().getUid();

        UserDataModel dataModel = new UserDataModel(currentUserID,
                mFName.getText().toString(),
                mLName.getText().toString(),
                mEmail.getText().toString(),
                mPhoneNumber.getText().toString(),
                mNationalID.getText().toString(),
                password);

        UsersRef.child(currentUserID).setValue(dataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.e("MyApps-Signup", "onComplete: data has beed saved successfully");
                    mCustomProgress.hideProgress();
                    Toast.makeText(Signup.this, "Please Login to your account", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Signup.this, Login.class));
                    finish();
                    mAuth.signOut();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(Signup.this, "Error Occurred :" + message, Toast.LENGTH_LONG).show();
                    mCustomProgress.hideProgress();
                }

            }
        });

    }

    //function to send verification email to the users email
    private void sendVerificationMessage() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.reload();
                        Toast.makeText(Signup.this, "Please check your email to verify your new account", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Signup.this, Login.class));
                        mAuth.signOut();
                        finish();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(Signup.this, "Error : " + error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    //function to check the validation of the data
    private boolean validate() {
        if (mFName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your first name", Toast.LENGTH_LONG).show();
            return false;
        } else if (mLName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your last name", Toast.LENGTH_LONG).show();
            return false;
        } else if (mEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your email address", Toast.LENGTH_LONG).show();
            return false;
        } else if (mPhoneNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your phone number", Toast.LENGTH_LONG).show();
            return false;
        }
//        else if (mPhoneNumber.getText().toString().length() < 11 || mPhoneNumber.getText().toString().length() > 11) {
//            Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_LONG).show();
//            return false;
//        }
        else if (mNationalID.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter youe national ID", Toast.LENGTH_LONG).show();
            return false;
        }
//        else if (mNationalID.getText().toString().length() < 14 || mNationalID.getText().toString().length() > 14) {
//            Toast.makeText(this, "Enter valid national ID", Toast.LENGTH_LONG).show();
//            return false;
//        }
        else if (mPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (mPassword.getText().toString().length() < 8) {
            mPassword.setError("Password < 8");
            Toast.makeText(this, "Your password cannot be less than 8 characters", Toast.LENGTH_LONG).show();
            return false;
        } else if (mConfirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please, Confirm your password", Toast.LENGTH_LONG).show();
            return false;
        } else if (!(mPassword.getText().toString().equals(mConfirmPassword.getText().toString()))) {
            mConfirmPassword.setError("Passwords do not matched");
            Toast.makeText(this, "Please, Confirm your password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}