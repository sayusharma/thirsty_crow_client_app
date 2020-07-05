package com.e.thirstycrow_clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.thirstycrow_clientapp.Model.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    Context context;
    ArrayList<Order> orders;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.each_order,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.orderid.setText(orders.get(position).getPayment_id());
        holder.phone.setText(orders.get(position).getPhone());
        holder.date.setText(orders.get(position).getDelivery_date());
        holder.time.setText(orders.get(position).getDelivery_time());
        holder.address.setText(orders.get(position).getAddress());
        holder.name.setText(orders.get(position).getFirst());
        holder.withCan.setText(orders.get(position).getProductWith().getQty());
        holder.withoutCan.setText(orders.get(position).getProductWithout().getQty());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView orderid,date,time,withCan,withoutCan,address,phone,name;
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
        }
    }
}
