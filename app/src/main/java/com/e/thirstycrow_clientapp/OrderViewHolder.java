package com.e.thirstycrow_clientapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.thirstycrow_clientapp.Interface.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView orderid,date,time,withCan,withoutCan,address,phone,name;
    private ItemClickListener itemClickListener;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderid = (TextView) itemView.findViewById(R.id.textOrderID);
        date = (TextView) itemView.findViewById(R.id.textDelDate);
        time = (TextView) itemView.findViewById(R.id.textDelTime);
        withCan = (TextView) itemView.findViewById(R.id.textWithCan);
        withoutCan = (TextView) itemView.findViewById(R.id.textWithoutCan);
        address = (TextView) itemView.findViewById(R.id.textAddress);
        phone = (TextView) itemView.findViewById(R.id.textPhone);
        name = (TextView) itemView.findViewById(R.id.textName);
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
