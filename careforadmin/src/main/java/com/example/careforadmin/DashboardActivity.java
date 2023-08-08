package com.example.careforadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {


    CardView statusCG;
    CardView statusClient;
    CardView NGO;
    CardView caregiver;
    CardView client;
    CardView faq;
    CardView quote;
    CardView booking;
    CardView payment;

    TextView clientcount;
    TextView caregivercount;
    TextView NGOcount;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

    statusCG = findViewById(R.id.statusCG);
    statusCG.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(DashboardActivity.this, Admin_CG_Approval.class);
            startActivity(i);
            finish();
        }
    });

        statusClient = findViewById(R.id.statusClient);
        statusClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(DashboardActivity.this, Admin_Client_Approval.class);
                startActivity(i);
                finish();
            }
        });

        NGO = findViewById(R.id.NGOs);
        NGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(DashboardActivity.this, CG_EditProfile.class);
//                startActivity(i);
//                finish();
            }
        });

        caregiver = findViewById(R.id.caregiver);
        caregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, Admin_CG_Display.class);
                startActivity(i);
                finish();
            }
        });

        client = findViewById(R.id.client);
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, Admin_Client_Display.class);
                startActivity(i);
                finish();
            }
        });

        faq = findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(DashboardActivity.this, CG_EditProfile.class);
//                startActivity(i);
//                finish();
            }
        });

        booking = findViewById(R.id.booking);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(DashboardActivity.this, CG_EditProfile.class);
//                startActivity(i);
//                finish();
            }
        });

        payment = findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(DashboardActivity.this, CG_EditProfile.class);
//                startActivity(i);
//                finish();
            }
        });

        quote = findViewById(R.id.quotes);
        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, QuoteActivity.class);
                startActivity(i);
                finish();
            }
        });

        clientcount = findViewById(R.id.clientcount);
        caregivercount = findViewById(R.id.cgcount);
        NGOcount = findViewById(R.id.ngocount);

        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    int count;
                    count = (int) snapshot.getChildrenCount();
                    caregivercount.setText(Integer.toString(count));
                }
                else{
                    caregivercount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int count;
                    count = (int) snapshot.getChildrenCount();
                    clientcount.setText(Integer.toString(count));
                }
                else{
                    clientcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("NGOs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int count;
                    count = (int) snapshot.getChildrenCount();
                    NGOcount.setText(Integer.toString(count));
                }
                else{
                    NGOcount.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed(){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit CareFor?")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            DashboardActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
    }



}
