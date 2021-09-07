package com.demo.myapps.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.myapps.DataModels.AccountDataModel;
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

public class UserAccountLaunch extends AppCompatActivity implements View.OnClickListener{

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mAccountRef;
    private String currentUserID;
    //Views
    TextView mAccNameToolBar, mAccName;
    ImageView mAccImage , mBackBtn;
    RelativeLayout mLaunchBtn , mLogoutBtn;
    //Data From Intent
    Intent intent;
    String accKey;
    AccountDataModel userAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_launch);


        initViews();

    }

    private void initViews() {

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        mAccountRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID).child("Accounts");

        //Views
        mAccName = findViewById(R.id.user_acc_acc_name);
        mAccNameToolBar = findViewById(R.id.user_acc_toolbar_name);
        mAccImage = findViewById(R.id.user_acc_image);
        mLaunchBtn = findViewById(R.id.user_acc_launch_btn);
        mLogoutBtn = findViewById(R.id.user_acc_logout_btn);
        mBackBtn = findViewById(R.id.user_acc_back_btn);
        mBackBtn.setOnClickListener(this);
        mLaunchBtn.setOnClickListener(this);
        mLogoutBtn.setOnClickListener(this);


        //Get Data From Intent
        intent = getIntent();
        accKey = intent.getStringExtra("accKey");
        userAcc = new AccountDataModel();
        getAccData();


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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_acc_launch_btn:
                Intent web = new Intent(UserAccountLaunch.this, WebView.class);
                web.putExtra("linkToWeb", userAcc.getLink());
                web.putExtra("email", userAcc.getAccEmail());
                web.putExtra("pass", userAcc.getAccPassword());
                startActivity(web);
                break;

            case R.id.user_acc_logout_btn:
                mAccountRef.child(accKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UserAccountLaunch.this, "The Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;

            case R.id.user_acc_back_btn:
                finish();
                break;
        }


    }
}