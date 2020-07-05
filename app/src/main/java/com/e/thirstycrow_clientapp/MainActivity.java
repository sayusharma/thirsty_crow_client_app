package com.e.thirstycrow_clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SaveSharedPreference.getUserName(getApplicationContext()).equals("")) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(MainActivity.this, DashActivity.class));
                    finish();
                }
            }
        },1500);
    }
}