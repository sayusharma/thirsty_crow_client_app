package com.e.thirstycrow_clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.e.thirstycrow_clientapp.Interface.ItemClickListener;
import com.e.thirstycrow_clientapp.Model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeliveredOrders extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewDelivered;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_orders);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("requests");
        recyclerViewDelivered = findViewById(R.id.recyclerDelivered);
        recyclerViewDelivered.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDelivered.setHasFixedSize(true);
        loadDeliveredOrders();
    }

    private void loadDeliveredOrders() {
        final FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Order,OrderViewHolder>(
                Order.class,R.layout.each_order,OrderViewHolder.class,databaseReference.orderByChild("status").equalTo("Delivered").orderByChild("delivery_date")
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, final Order order, int i) {
                orderViewHolder.address.setText(order.getAddress());
                orderViewHolder.date.setText(order.getDelivery_date());
                orderViewHolder.name.setText(order.getFirst());
                orderViewHolder.orderid.setText(order.getOrderid());
                orderViewHolder.phone.setText(order.getPhone());
                orderViewHolder.time.setText(order.getDelivery_time());
                orderViewHolder.withCan.setText(order.getProductWith().getQty());
                orderViewHolder.withoutCan.setText(order.getProductWithout().getQty());
                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recyclerViewDelivered.setAdapter(adapter);
    }
}