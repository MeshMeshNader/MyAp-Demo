package com.demo.myapps.HomeApp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.myapps.HomeApp.Accounts.AccountsAdapter;
import com.demo.myapps.HomeApp.Accounts.AccountsModel;
import com.demo.myapps.R;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {


    AccountsAdapter accountsAdapter;
    RecyclerView recyclerView;
    ImageView mAddBtn, mProfileBtn;
    ArrayList<AccountsModel> mAccList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.acc_rv);
        mAddBtn = findViewById(R.id.home_add_btn);
        mAddBtn.setOnClickListener(this);
        mProfileBtn = findViewById(R.id.home_profile_btn);
        mProfileBtn.setOnClickListener(this);

        mAccList.add(new AccountsModel("Gmail", R.drawable.ic_gmail));
        mAccList.add(new AccountsModel("Instagram", R.drawable.ic_instagram));
        mAccList.add(new AccountsModel("Google +", R.drawable.ic_google__));
        mAccList.add(new AccountsModel("Linked in", R.drawable.ic_linked_in));
        mAccList.add(new AccountsModel("Messenger", R.drawable.ic_massenger));
        mAccList.add(new AccountsModel("Skype", R.drawable.ic_skype));
        mAccList.add(new AccountsModel("Snapchat", R.drawable.ic_snapchat));
        mAccList.add(new AccountsModel("Telegram", R.drawable.ic_telegram));
        mAccList.add(new AccountsModel("Twitter", R.drawable.ic_twitter));
        mAccList.add(new AccountsModel("Whatsapp", R.drawable.ic_whatsapp));
        mAccList.add(new AccountsModel("Youtube", R.drawable.ic_youtube));
        mAccList.add(new AccountsModel("Facebook", R.drawable.ic_fb));

        accountsAdapter = new AccountsAdapter(mAccList);
        recyclerView.setAdapter(accountsAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void showSelectAccountDialog() {
        AlertDialog.Builder selectionDialog = new AlertDialog.Builder(this);
        selectionDialog.setTitle("Choose Your Account");
        final String[] selectDialogItem = {"Gmail", "Google +", "Facebook", "Linked in", "Instagram", "Messenger", "Skype", "Snapchat", "Telegram", "Twitter", "Whatsapp", "Youtube"};

        selectionDialog.setItems(selectDialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(Home.this, selectDialogItem[0], Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(Home.this, selectDialogItem[1], Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(Home.this, selectDialogItem[2], Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(Home.this, selectDialogItem[3], Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(Home.this, selectDialogItem[4], Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(Home.this, selectDialogItem[5], Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(Home.this, selectDialogItem[6], Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(Home.this, selectDialogItem[7], Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(Home.this, selectDialogItem[8], Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(Home.this, selectDialogItem[9], Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        Toast.makeText(Home.this, selectDialogItem[9], Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                        Toast.makeText(Home.this, selectDialogItem[9], Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        selectionDialog.show();
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