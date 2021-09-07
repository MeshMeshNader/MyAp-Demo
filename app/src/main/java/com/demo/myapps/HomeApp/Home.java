package com.demo.myapps.HomeApp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.myapps.DataModels.AccountDataModel;
import com.demo.myapps.HomeApp.Accounts.AccountsAdapter;
import com.demo.myapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mAccountRef;
    private String currentUserID;


    //Views
    AccountsAdapter accountsAdapter;
    RecyclerView recyclerView;
    ImageView mAddBtn, mProfileBtn;
    ArrayList<AccountDataModel> mAccList = new ArrayList<>();
    ProgressDialog progressDialog;

    //Data
    AccountDataModel acc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
    }

    private void initViews() {

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        mAccountRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID).child("Accounts");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Views
        recyclerView = findViewById(R.id.acc_rv);
        mAddBtn = findViewById(R.id.home_add_btn);
        mAddBtn.setOnClickListener(this);
        mProfileBtn = findViewById(R.id.home_profile_btn);
        mProfileBtn.setOnClickListener(this);
        getAllAccounts();



    }


    private void showSelectAccountDialog() {
        AlertDialog.Builder selectionDialog = new AlertDialog.Builder(this);
        selectionDialog.setTitle("Choose Your Account");
        String[] selectDialogItem = {"Gmail", "Google +", "Facebook", "Linked in", "Instagram", "Messenger", "Skype", "Snapchat", "Telegram", "Twitter", "Whatsapp", "Youtube"};


        selectionDialog.setItems(selectDialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accKey = mAccountRef.push().getKey();
                switch (which) {
                    case 0:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.gmail), R.drawable.ic_gmail, getResources().getString(R.string.gmail_link));
                        mAccList.add(acc);
                        break;
                    case 1:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.google_plus), R.drawable.ic_google__, getResources().getString(R.string.google_plus_link));
                        mAccList.add(acc);
                        break;
                    case 2:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.facebook), R.drawable.ic_fb, getResources().getString(R.string.facebook_link));
                        mAccList.add(acc);
                        break;
                    case 3:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.linked_in), R.drawable.ic_linked_in, getResources().getString(R.string.linked_in_link));
                        mAccList.add(acc);
                        break;
                    case 4:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.instagram), R.drawable.ic_instagram, getResources().getString(R.string.instagram_link));
                        mAccList.add(acc);
                        break;
                    case 5:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.messenger), R.drawable.ic_massenger, getResources().getString(R.string.messenger_link));
                        mAccList.add(acc);
                        break;
                    case 6:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.skype), R.drawable.ic_skype, getResources().getString(R.string.skype_link));
                        mAccList.add(acc);
                        break;
                    case 7:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.snapchat), R.drawable.ic_snapchat, getResources().getString(R.string.snapchat_link));
                        mAccList.add(acc);
                        break;
                    case 8:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.telegram), R.drawable.ic_telegram, getResources().getString(R.string.telegram_link));
                        mAccList.add(acc);
                        break;
                    case 9:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.twitter), R.drawable.ic_twitter, getResources().getString(R.string.twitter_link));
                        mAccList.add(acc);
                        break;
                    case 10:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.whatsapp), R.drawable.ic_whatsapp, getResources().getString(R.string.whatsapp_link));
                        mAccList.add(acc);
                        break;
                    case 11:
                        acc = new AccountDataModel(accKey, "", "", getResources().getString(R.string.youtube), R.drawable.ic_youtube, getResources().getString(R.string.youtube_link));
                        mAccList.add(acc);
                        break;
                }
                //Refresh RecyclerView
                accountsAdapter = new AccountsAdapter(Home.this, mAccList);
                recyclerView.setAdapter(accountsAdapter);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Home.this, 3);
                recyclerView.setLayoutManager(layoutManager);

                //Save the selected new account to the database
                mAccountRef.child(accKey).setValue(acc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Home.this, "Account Added Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("TAG", "onComplete: " + task.getException().toString());
                        }
                    }
                });

            }
        });
        selectionDialog.show();
    }

    //Get all saved accounts from the database and save it in the array
    private void getAllAccounts() {
        mAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAccList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnap : snapshot.getChildren()) {
                        mAccList.add(childSnap.getValue(AccountDataModel.class));
                    }
                    accountsAdapter = new AccountsAdapter(Home.this, mAccList);
                    recyclerView.setAdapter(accountsAdapter);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Home.this, 3);
                    recyclerView.setLayoutManager(layoutManager);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_add_btn:
                showSelectAccountDialog();
                break;
            case R.id.home_profile_btn:

                break;
        }
    }
}