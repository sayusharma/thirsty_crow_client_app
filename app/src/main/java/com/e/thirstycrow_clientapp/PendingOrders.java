package com.e.thirstycrow_clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.thirstycrow_clientapp.Interface.ItemClickListener;
import com.e.thirstycrow_clientapp.Model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PendingOrders extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ArrayList<Order> arrayList;
    private RecyclerView recyclerView;
    private int currentAction;
    private OrderAdapter orderAdapter;
    private Context context;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);
        context = this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.recyclerPending);
        databaseReference = (DatabaseReference) firebaseDatabase.getReference("requests");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        loadPendingOrders();

    }

    private void loadPendingOrders() {
        FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Order,OrderViewHolder>(
                Order.class,R.layout.each_order,OrderViewHolder.class,databaseReference.orderByChild("status").equalTo("pending")
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
                final Order clickItem = order;
                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        OrderViewDialog orderViewDialog = new OrderViewDialog(clickItem);
                        orderViewDialog.showDialog((Activity) context);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
    public class OrderViewDialog extends AppCompatActivity{
        public LinearLayout share,delivered,cancel;
        public Order clickItem;

        public OrderViewDialog(Order clickItem) {
            this.clickItem = clickItem;
        }

        public void showDialog(final Activity activity){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_builder);
            share = dialog.findViewById(R.id.linearShare);
            delivered = dialog.findViewById(R.id.linearDelivered);
            cancel = dialog.findViewById(R.id.linearCancel);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    sendMessage(clickItem);
                }
            });
            delivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    changetoDelivered(clickItem);

                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private void changetoDelivered(Order clickItem) {
        DatabaseReference reference = firebaseDatabase.getReference("requests");
        reference.child(clickItem.getOrderid()).child("status").setValue("delivered").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    private void sendMessage(Order clickItem) {
        String text = "Qty with Can:" + clickItem.getProductWith().getQty() + "Qty without Can : " + clickItem.getProductWithout().getQty() +
                "\n Name : " + clickItem.getFirst() + " Phone No. : " + clickItem.getPhone();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }
}