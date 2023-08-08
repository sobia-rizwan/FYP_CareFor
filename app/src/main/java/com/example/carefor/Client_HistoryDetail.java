package com.example.carefor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Client_HistoryDetail extends AppCompatActivity {

    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    String user;
    String location;
    String dates;
    String name;
    String Gender;
    String mobile;
    String book_status;

    TextView detailTitle, detailName, detailCNIC, detailStatus;
    TextView detailGig, detailRate, detailTime, detailAddress, detailMobile, detailDate;
    ImageView BackBtn;
    Button End_job;
    String key = "";
    int i = 1;
    String a;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history_detail);

        preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME, null);

        detailTitle = findViewById(R.id.client_user_name);
        detailGig = findViewById(R.id.job_type);
        detailTime = findViewById(R.id.shift);
        detailName = findViewById(R.id.client_full_name);
        detailCNIC = findViewById(R.id.client_Cnic);
        detailAddress = findViewById(R.id.address1);
        detailMobile = findViewById(R.id.client_mobile);
        detailRate = findViewById(R.id.ratee);
        detailDate = findViewById(R.id.date_confirm);
        detailStatus = findViewById(R.id.Book);
        BackBtn = findViewById(R.id.back);
        End_job = findViewById(R.id.endJob);



        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               /* String s=(new StringBuilder()).append(user).append(detailTitle.getText().toString()).toString();
                System.out.println("BOOKING NAME: "+s);*/

                mobile = snapshot.child(user).child("Phone_Number").getValue(String.class);
                name = snapshot.child(user).child("Full_Name").getValue(String.class);

                /*book_status = snapshot.child(user).child("Booking").child(detailTitle.getText().toString()).child("Booking_Status").getValue(String.class);
                detailStatus.setText(book_status);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitle.setText(bundle.getString("Title"));
            detailName.setText(bundle.getString("Full Name"));
            detailDate.setText(bundle.getString("Date"));
            detailGig.setText(bundle.getString("Gig"));
            detailRate.setText(bundle.getString("Rate"));
            detailMobile.setText(bundle.getString("Phone Number"));
            detailStatus.setText(bundle.getString("Booking Status"));
            detailAddress.setText(bundle.getString("Address"));
            detailTime.setText(bundle.getString("Shift"));
            /*detailAddress.setText(location);
            detailDate.setText(bundle.getString(dates));*/
            key = bundle.getString("key");
        }


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Client_HistoryDetail.this, Client_HistoryActivity.class);
                startActivity(i);
                finish();
            }
        });

        End_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailStatus.getText().toString().equals("Completed")) {
                    Toast.makeText(getApplicationContext(), "Job already completed", Toast.LENGTH_LONG).show();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Client_HistoryDetail.this);
                    builder.setMessage("Do you want to end job?")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            databaseReference.child("Client").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Booking_Status").setValue("Completed");
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Booking_Status").setValue("Completed");

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    databaseReference.child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String s=(new StringBuilder()).append(user).append(detailTitle.getText().toString()).toString();

                                            databaseReference.child("Booking").child(s).child("Booking_Status").setValue("Completed");
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    Intent i1 = new Intent(Client_HistoryDetail.this, ClientDashBoardActivity.class);
                                    startActivity(i1);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

}