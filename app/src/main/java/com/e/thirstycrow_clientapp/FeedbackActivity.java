package com.e.thirstycrow_clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.thirstycrow_clientapp.Interface.ItemClickListener;
import com.e.thirstycrow_clientapp.Model.Feedback;
import com.e.thirstycrow_clientapp.Model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class FeedbackActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        context=this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("complaints").child("pending");
        recyclerView = findViewById(R.id.recyclerFeedback);
       // databaseReference = firebaseDatabase.getReference().child("requests").child("pending");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        loadComplaints();
    }

    private void loadComplaints() {
        FirebaseRecyclerAdapter<Feedback,FeedbackViewHolder> adapter = new FirebaseRecyclerAdapter<Feedback, FeedbackViewHolder>(
                Feedback.class,R.layout.feedback_item,FeedbackViewHolder.class,databaseReference.orderByKey()
        ) {
            @Override
            protected void populateViewHolder(FeedbackViewHolder feedbackViewHolder, final Feedback feedback, int i) {
                feedbackViewHolder.subject.setText(feedback.getSubject());
                feedbackViewHolder.phone.setText(feedback.getPhone());
                feedbackViewHolder.name.setText(feedback.getName());
                feedbackViewHolder.desc.setText(feedback.getDesc());
                final Feedback clickItem = feedback;
                feedbackViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                      //  Toast.makeText(context,clickItem.getDesc(),Toast.LENGTH_LONG).show();
                           FeedbackViewDialog feedbackViewDialog = new FeedbackViewDialog(clickItem);
                           feedbackViewDialog.showDialog((Activity) context);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
    public class FeedbackViewDialog extends AppCompatActivity{
        public LinearLayout resolved,cancel;
        public Feedback clickItem;
        public FeedbackViewDialog(Feedback clickItem) {
            this.clickItem = clickItem;
        }
        public void showDialog(final Activity activity){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_feedback);
            resolved = dialog.findViewById(R.id.linearResolved);
            cancel = dialog.findViewById(R.id.linearFeedCancel);
            resolved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //sendMessage(clickItem);
                    markAsResolved(clickItem);
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

    private void markAsResolved(Feedback clickItem) {
        DatabaseReference reference = databaseReference;
        reference.child(clickItem.getComplaintId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        DatabaseReference reference1 = firebaseDatabase.getReference("complaints").child("resolved");
        reference1.child(String.valueOf(Calendar.getInstance().getTimeInMillis())).setValue(clickItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
}