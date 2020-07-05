package com.e.thirstycrow_clientapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BusinessInsightAcitivity extends AppCompatActivity {
    private DatePickerDialog pickerDialog,pickerDialogTo;
    private TextView fromDate,toDate;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference delivered;
    private Spinner spinner;
    @Override
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_insight_acitivity);
        context = this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        spinner = findViewById(R.id.intervalSpinner);
        delivered = firebaseDatabase.getReference("requests").child("delivered");
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(context,
                R.array.time_interval, android.R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapters);
    }
    public void onClickGetInsights(View view) throws ParseException {
        if (spinner.getSelectedItem().toString().equals("Select Time Interval")){
            Toast.makeText(context,"Select Interval",Toast.LENGTH_SHORT).show();
        }
        else{
            Calendar calendar = Calendar.getInstance();
            final TextView textView = findViewById(R.id.soldWithCan);
            DatabaseReference reference = delivered;
            final TextView textView1= findViewById(R.id.soldWithoutCan);
            switch (spinner.getSelectedItem().toString()){
                case "Today":
                   // Calendar calendar = Calendar.getInstance();
                    java.sql.Date date = new java.sql.Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                    final ArrayList<Integer> arrayList = new ArrayList<>();
                    final ArrayList<Integer> arrayList2 = new ArrayList<>();
                    reference.orderByChild("delivery_date").equalTo(date.toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                String tmp = (String) dataSnapshot1.child("productWith").child("qty").getValue();
                                String tmp2 = (String) dataSnapshot1.child("productWithout").child("qty").getValue();
                                arrayList.add(Integer.parseInt(tmp));
                                arrayList2.add(Integer.parseInt(tmp2));
                            }
                            int count=0;
                            for (int i=0;i<arrayList.size();i++){
                                count+=arrayList.get(i);
                            }
                            textView.setText(String.valueOf(count));
                            count = 0;
                            for (int i=0;i<arrayList2.size();i++){
                                count+=arrayList2.get(i);
                            }

                            textView1.setText(String.valueOf(count));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;
                case "Yesterday":
                    calendar.add(Calendar.DAY_OF_MONTH,-1);
                    java.sql.Date date1 = new java.sql.Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                    final ArrayList<Integer> arrayList3 = new ArrayList<>();
                    final ArrayList<Integer> arrayList4 = new ArrayList<>();
                    reference.orderByChild("delivery_date").equalTo(date1.toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                String tmp = (String) dataSnapshot1.child("productWith").child("qty").getValue();
                                String tmp2 = (String) dataSnapshot1.child("productWithout").child("qty").getValue();
                                arrayList3.add(Integer.parseInt(tmp));
                                arrayList4.add(Integer.parseInt(tmp2));
                            }
                            int count=0;
                            for (int i=0;i<arrayList3.size();i++){
                                count+=arrayList3.get(i);
                            }
                            textView.setText(String.valueOf(count));
                            count = 0;
                            for (int i=0;i<arrayList4.size();i++){
                                count+=arrayList4.get(i);
                            }
                            textView1.setText(String.valueOf(count));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;
                case "Last 3 days":
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Gathering Info...");
                    progressDialog.show();
                    int j=0;
                    final ArrayList<Integer> with = new ArrayList<>();
                    final ArrayList<Integer> without = new ArrayList<>();
                    //final int[] count = {0};
                   // final int[] count2 = {0};
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                    final ArrayList<Integer> arrayList5 = new ArrayList<>();
                    final ArrayList<Integer> arrayList6 = new ArrayList<>();
                    while(j<3){
                        calendar.add(Calendar.DAY_OF_MONTH,-1);
                        date1 = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        Log.i("date",date1.toString());
                        reference.orderByChild("delivery_date").equalTo(date1.toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    String tmp = (String) dataSnapshot1.child("productWith").child("qty").getValue();
                                    String tmp2 = (String) dataSnapshot1.child("productWithout").child("qty").getValue();
                                    Log.i("tmp1",tmp);
                                    Log.i("tmp2",tmp2);
                                    arrayList5.add(Integer.parseInt(tmp));
                                    arrayList6.add(Integer.parseInt(tmp2));
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        j++;
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("size",String.valueOf(arrayList5.size()));
                            int count=0;
                            for (int i=0;i<arrayList5.size();i++){
                                count+=arrayList5.get(i);
                            }
                            textView.setText(String.valueOf(count));
                            count = 0;
                            for (int i=0;i<arrayList6.size();i++){
                                count+=arrayList6.get(i);
                            }
                            textView1.setText(String.valueOf(count));
                            progressDialog.dismiss();
                        }
                    },4000);
                    break;
                case "This Month":
                    final ProgressDialog progressDialog1 = new ProgressDialog(this);
                    progressDialog1.setMessage("Gathering Info...");
                    progressDialog1.show();
                    int j1=0;
                    final ArrayList<Integer> with1 = new ArrayList<>();
                    final ArrayList<Integer> without3 = new ArrayList<>();
                    //final int[] count = {0};
                    // final int[] count2 = {0};
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                    final ArrayList<Integer> arrayList7 = new ArrayList<>();
                    final ArrayList<Integer> arrayList8 = new ArrayList<>();
                    while(j1<30){
                        calendar.add(Calendar.DAY_OF_MONTH,-1);
                        date1 = new java.sql.Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        Log.i("date",date1.toString());
                        reference.orderByChild("delivery_date").equalTo(date1.toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    String tmp = (String) dataSnapshot1.child("productWith").child("qty").getValue();
                                    String tmp2 = (String) dataSnapshot1.child("productWithout").child("qty").getValue();
                                    Log.i("tmp1",tmp);
                                    Log.i("tmp2",tmp2);
                                    arrayList7.add(Integer.parseInt(tmp));
                                    arrayList8.add(Integer.parseInt(tmp2));
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        j1++;
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("size",String.valueOf(arrayList7.size()));
                            int count=0;
                            for (int i=0;i<arrayList7.size();i++){
                                count+=arrayList7.get(i);
                            }
                            textView.setText(String.valueOf(count));
                            count = 0;
                            for (int i=0;i<arrayList8.size();i++){
                                count+=arrayList8.get(i);
                            }
                            textView1.setText(String.valueOf(count));
                            progressDialog1.dismiss();
                        }
                    },4000);
                    break;
            }
        }
    }
    public static ArrayList<Date> getDatesBetweenUsingJava7(
            Date startDate, Date endDate) {
        ArrayList<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }
}