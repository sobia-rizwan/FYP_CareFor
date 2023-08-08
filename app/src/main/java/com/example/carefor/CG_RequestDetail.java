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

public class CG_RequestDetail extends AppCompatActivity {

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
    TextView detailGig, detailRate, detailTime, detailAddress, detailMobile, detailDate ;
    ImageView BackBtn;
    Button accept_btn, reject_btn;
    String key = "";
    int i = 1;
    String a;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cg_request_detail);

        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);

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
        accept_btn = findViewById(R.id.accept);
        reject_btn = findViewById(R.id.reject);



        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               /* String s=(new StringBuilder()).append(user).append(detailTitle.getText().toString()).toString();
                System.out.println("BOOKING NAME: "+s);*/

                mobile = snapshot.child(user).child("Phone_Number").getValue(String.class);
                name = snapshot.child(user).child("Full_Name").getValue(String.class);



                book_status = snapshot.child(user).child("Booking").child(detailTitle.getText().toString()).child("Booking_Status").getValue(String.class);
                detailStatus.setText(book_status);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
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

        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Gender = snapshot.child(detailTitle.getText().toString()).child("Gender").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CG_RequestDetail.this, CGDashBoardActivity.class);
                startActivity(i);
                finish();
            }
        });

        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailStatus.getText().toString().equals("Accepted")){
                    Toast.makeText(getApplicationContext(), "Already Accepted", Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CG_RequestDetail.this);
                    builder.setMessage("Do you accept?")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                   //reject_btn.setVisibility(View.GONE);
                                    databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Booking_Status").setValue("Accepted");
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Caregiver_UserName").setValue(user);
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Caregiver_Name").setValue(name);
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Date").setValue(detailDate.getText().toString());
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Caregiver_Number").setValue(mobile);
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Gig").setValue(detailGig.getText().toString());
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Rate").setValue(detailRate.getText().toString());
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Shift").setValue(detailTime.getText().toString());
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Client_UserName").setValue(detailTitle.getText().toString());
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Client_FullName").setValue(detailName.getText().toString());
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Client_PhoneNumber").setValue(detailMobile.getText().toString());
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Client_Gender").setValue(Gender);
                                            databaseReference.child("Caregiver").child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Client_Address").setValue(detailAddress.getText().toString());

                                            databaseReference.child("Caregiver").child(user).child("Booking").child(detailTitle.getText().toString()).removeValue();
                                        }


                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Booking_Status").setValue("Accepted");
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Caregiver_UserName").setValue(user);
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Caregiver_Name").setValue(name);
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Date").setValue(detailDate.getText().toString());
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Caregiver_Number").setValue(mobile);
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Gig").setValue(detailGig.getText().toString());
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Rate").setValue(detailRate.getText().toString());
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Shift").setValue(detailTime.getText().toString());
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Client_UserName").setValue(detailTitle.getText().toString());
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Client_FullName").setValue(detailName.getText().toString());
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Client_PhoneNumber").setValue(detailMobile.getText().toString());
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Client_Gender").setValue(Gender);
                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Accepted_Booking").child(user).child("Client_Address").setValue(detailAddress.getText().toString());

                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Booking").child(user).removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    databaseReference.child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String s=(new StringBuilder()).append(detailTitle.getText().toString()).append(user).toString();

                                            databaseReference.child("Booking").child(s).child("Booking_Status").setValue("Accepted");
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Intent i1 = new Intent(CG_RequestDetail.this, CG_HistoryActivity.class);
                                    startActivity(i1);


                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });
        reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailStatus.getText().toString().equals("Rejected")){
                    Toast.makeText(getApplicationContext(), "Already Rejected", Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CG_RequestDetail.this);
                    builder.setMessage("Do you want to reject?")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            databaseReference.child("Caregiver").child(user).child("Booking").child(detailTitle.getText().toString()).removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            databaseReference.child("Client").child(detailTitle.getText().toString()).child("Booking").child(user).removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    databaseReference.child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String s=(new StringBuilder()).append(detailTitle.getText().toString()).append(user).toString();
                                            databaseReference.child("Booking").child(s).child("Booking_Status").setValue("Rejected");
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Intent i1 = new Intent(CG_RequestDetail.this, CG_HistoryActivity.class);
                                    startActivity(i1);


                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
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