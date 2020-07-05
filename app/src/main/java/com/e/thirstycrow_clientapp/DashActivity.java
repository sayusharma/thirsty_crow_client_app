package com.e.thirstycrow_clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
    }
    public void onClickPendingOrders(View view){
        startActivity(new Intent(DashActivity.this,PendingOrders.class));
    }
    public void onClickPendingOrdersForToday(View view){
        startActivity(new Intent(DashActivity.this,TodayPendingOrders.class));
    }
    public void onClickDeliveredOrders(View view){
        startActivity(new Intent(DashActivity.this,DeliveredOrders.class));
    }
    public void onClickFeedback(View view){
        startActivity(new Intent(DashActivity.this,FeedbackActivity.class));
    }
    public void onClickBusinessInsights(View view){
        startActivity(new Intent(DashActivity.this,BusinessInsightAcitivity.class));
    }
    public void onClickLogout(View view){
        SaveSharedPreference.clearPreference(getApplicationContext());
        startActivity(new Intent(DashActivity.this,LoginActivity.class));
    }
}