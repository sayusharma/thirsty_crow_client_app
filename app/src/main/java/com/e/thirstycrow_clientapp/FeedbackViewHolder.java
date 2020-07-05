package com.e.thirstycrow_clientapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.thirstycrow_clientapp.Interface.ItemClickListener;

public class FeedbackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView subject,desc,name,phone;
    private ItemClickListener itemClickListener;
    public FeedbackViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.textComName);
        phone = (TextView) itemView.findViewById(R.id.textComPhone);
        subject = (TextView) itemView.findViewById(R.id.textSubject);
        desc = (TextView) itemView.findViewById(R.id.textDesc);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
