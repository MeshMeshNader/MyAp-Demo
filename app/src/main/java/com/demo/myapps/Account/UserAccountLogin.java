package com.demo.myapps.Account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.myapps.DataModels.AccountDataModel;
import com.demo.myapps.HomeApp.Accounts.AccountsAdapter;
import com.demo.myapps.HomeApp.Home;
import com.demo.myapps.R;
import com.demo.myapps.WebView.WebView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountLogin extends AppCompatActivity {

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mAccountRef;
    private String currentUserID;
    //Views
    TextView mAccNameToolBar, mAccName;
    ImageView mAccImage, mBackBtn;
    EditText mAccEmail, mAccPassword;
    Button mLoginBtn;
    //Data From Intent
    Intent intent;
    String accKey;
    AccountDataModel userAcc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_login);

        initViews();
    }

    private void initViews() {


        //Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        mAccountRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID).child("Accounts");

        //Views
        mAccName = findViewById(R.id.user_account_acc_name);
        mAccNameToolBar = findViewById(R.id.user_account_toolbar_name);
        mAccImage = findViewById(R.id.user_account_image);
        mAccEmail = findViewById(R.id.user_account_email_et);
        mAccPassword = findViewById(R.id.user_account_password_et);
        mLoginBtn = findViewById(R.id.user_account_login_btn);

        mBackBtn = findViewById(R.id.user_account_back_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Get Data From Intent
        intent = getIntent();
        accKey = intent.getStringExtra("accKey");
        userAcc = new AccountDataModel();
        getAccData();


        //Login Btn (Save Data to FireBase)
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              saveAccountData();
            }
        });
    }

    private void getAccData() {
        mAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnap : snapshot.getChildren()) {
                        if(childSnap.getValue(AccountDataModel.class).getAccId().equals(accKey))
                            userAcc = childSnap.getValue(AccountDataModel.class);
                    }
                    mAccName.setText(userAcc.getAccName());
                    mAccNameToolBar.setText(userAcc.getAccName());
                    mAccImage.setImageResource(userAcc.getAccImg());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveAccountData(){


        mAccountRef.child(accKey).child("accEmail").setValue(mAccEmail.getText().toString());
        mAccountRef.child(accKey).child("accPassword").setValue(mAccPassword.getText().toString());
        Intent web = new Intent(UserAccountLogin.this, WebView.class);
        web.putExtra("linkToWeb", userAcc.getLink());
        web.putExtra("email", mAccEmail.getText().toString());
        web.putExtra("pass", mAccPassword.getText().toString());
        startActivity(web);
        finish();

    }


}