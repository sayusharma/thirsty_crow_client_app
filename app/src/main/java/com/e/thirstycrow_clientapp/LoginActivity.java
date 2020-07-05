package com.e.thirstycrow_clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.textEmailAddress);
        pass = findViewById(R.id.textPassword);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("admin");


    }
    public String encodeEmail(String email){
        return email.replace(".",",");
    }
    public void onClickLogin(View view){
        if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(pass.getText())){
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        }
        else{
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(encodeEmail(email.getText().toString())).exists()){
                        String password = (String) dataSnapshot.child(encodeEmail(email.getText().toString())).child("password").getValue();
                        if (password.equals(pass.getText().toString())){
                            SaveSharedPreference.setUserName(getApplicationContext(),email.getText().toString());
                            startActivity(new Intent(LoginActivity.this,DashActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"PASSWORD DOES NOT MATCH",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"ADMIN DOESN'T EXISTS",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}