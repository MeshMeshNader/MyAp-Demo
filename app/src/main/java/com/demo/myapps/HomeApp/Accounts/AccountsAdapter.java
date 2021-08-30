package com.demo.myapps.HomeApp.Accounts;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.myapps.R;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccViewHolder>{

    ArrayList<AccountsModel> mAccList;

    public AccountsAdapter(ArrayList<AccountsModel> mAccList) {
        this.mAccList = mAccList;
    }

    @NonNull
    @Override
    public AccViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.acc_item , parent , false);
        AccViewHolder accViewHolder = new AccViewHolder(view);
        return accViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccViewHolder holder, int position) {
        holder.mAccText.setText(mAccList.get(position).accText);
        holder.mAccImage.setImageResource(mAccList.get(position).accImg);
    }

    @Override
    public int getItemCount() {
        return mAccList.size();
    }

    class AccViewHolder extends RecyclerView.ViewHolder{

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
