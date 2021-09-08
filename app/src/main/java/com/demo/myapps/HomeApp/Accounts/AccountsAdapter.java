package com.demo.myapps.HomeApp.Accounts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.myapps.Account.UserAccountLaunch;
import com.demo.myapps.Account.UserAccountLogin;
import com.demo.myapps.DataModels.AccountDataModel;
import com.demo.myapps.HomeApp.Home;
import com.demo.myapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccViewHolder> {

    FirebaseAuth mAuth;
    DatabaseReference UsersRef;
    String currentUserID;
    ArrayList<AccountDataModel> mAccList;
    Context context;

    public AccountsAdapter(Context context, ArrayList<AccountDataModel> mAccList) {
        this.mAccList = mAccList;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID).child("Accounts");
    }

    @NonNull
    @Override
    public AccViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.acc_item, parent, false);
        AccViewHolder accViewHolder = new AccViewHolder(view);
        return accViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccViewHolder holder, int position) {
        holder.mAccText.setText(mAccList.get(position).getAccName());
        holder.mAccImage.setImageResource(mAccList.get(position).getAccImg());
        holder.mAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mAccList.get(position).getAccEmail().isEmpty() && !mAccList.get(position).getAccPassword().isEmpty()) {
                    Intent intent = new Intent(context, UserAccountLaunch.class);
                    intent.putExtra("accKey", mAccList.get(position).getAccId());
                    context.startActivity(intent);
                } else if (mAccList.get(position).getAccEmail().isEmpty() || mAccList.get(position).getAccPassword().isEmpty()) {
                    if (mAccList.get(position).getAccName().equals(context.getResources().getString(R.string.whatsapp))
                            || mAccList.get(position).getAccName().equals(context.getResources().getString(R.string.telegram))) {
                        Intent intent = new Intent(context, UserAccountLaunch.class);
                        intent.putExtra("accKey", mAccList.get(position).getAccId());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, UserAccountLogin.class);
                        intent.putExtra("accKey", mAccList.get(position).getAccId());
                        context.startActivity(intent);
                    }
                }

            }
        });

        holder.mAccBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Do you want to delete this account!")
                        .setCancelable(false)
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UsersRef.child(mAccList.get(position).getAccId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        mAccList.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Account Deleted Successfully", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No!", null)
                        .show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAccList.size();
    }

    class AccViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mAccBtn;
        ImageView mAccImage;
        TextView mAccText;

        public AccViewHolder(@NonNull View itemView) {
            super(itemView);
            mAccBtn = itemView.findViewById(R.id.item_acc_btn);
            mAccImage = itemView.findViewById(R.id.item_account_image);
            mAccText = itemView.findViewById(R.id.item_account_text);
        }
    }


}
