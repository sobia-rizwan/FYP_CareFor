package com.example.carefor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Client_RequestDetail extends AppCompatActivity {

    //PAGE: activity_detail
    //activity_client_cg_detail this is not being used now but dont delete it. !!


    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    String user;
    String location;
    String dates;
    String book_status;


    TextView detailTitle, detailName, detailCNIC, detailStatus;
    TextView detailGig, detailRate, detailTime, detailAddress, detailMobile, detailDate ;
    ImageView BackBtn;
    Button Save_btn;
    String key = "";
    int i = 1;
    String a;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_request_detail);


        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);



        /*detailTitle = findViewById(R.id.detailTitle);
        detailName = findViewById(R.id.detailName);
        detailCNIC = findViewById(R.id.detailCNIC);
        detailEmail= findViewById(R.id.detailEmail);
        detailExp = findViewById(R.id.detailEXP);
        detailClientG= findViewById(R.id.detailClientGender);
        detailDesc = findViewById(R.id.detailDescription);
        detailGig = findViewById(R.id.detailGIG);
        detailRate = findViewById(R.id.detailRate);
        detailTime = findViewById(R.id.detailTime);*/

        detailTitle = findViewById(R.id.userr_name);
        detailGig = findViewById(R.id.GigsType);
        detailTime = findViewById(R.id.timee);
        detailName = findViewById(R.id.full_names);
        detailCNIC = findViewById(R.id.CNICs);
        detailAddress = findViewById(R.id.address1);
        detailMobile = findViewById(R.id.mobile);
        detailRate = findViewById(R.id.ratee);
        detailDate = findViewById(R.id.date);
        detailStatus = findViewById(R.id.Book);
        BackBtn = findViewById(R.id.back);


        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String s=(new StringBuilder()).append(user).append(detailTitle.getText().toString()).toString();
                System.out.println("BOOKING NAME: "+s);

                location = snapshot.child(user).child("Address").getValue(String.class);
                System.out.println("LOCATION: "+location);
                /*dates = snapshot.child("Booking").child(s).child("Rate").getValue(String.class);
                ;*/

                dates = snapshot.child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Date").getValue(String.class);
                System.out.println("DATE: "+dates);

                book_status = snapshot.child(user).child("Accepted_Booking").child(detailTitle.getText().toString()).child("Booking_Status").getValue(String.class);
                detailStatus.setText(book_status);
                detailAddress.setText(location);
                detailDate.setText(dates);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            detailTitle.setText(bundle.getString("Title"));
            detailName.setText(bundle.getString("Full Name"));
            detailCNIC.setText(bundle.getString("CNIC"));
            detailGig.setText(bundle.getString("Gig"));
            detailRate.setText(bundle.getString("Rate"));
            detailTime.setText(bundle.getString("Time"));
            detailMobile.setText(bundle.getString("Phone Number"));
            /*detailAddress.setText(location);
            detailDate.setText(bundle.getString(dates));*/
            key = bundle.getString("key");
        }

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Client_RequestDetail.this, ClientDashBoardActivity.class);
                startActivity(i);
            }
        });






/*

        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookingDialog();
            }
        });
*/
/*
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Caregiver");
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_CG_Detail.this);
                    builder.setMessage("Do you want to delete this record?")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    reference.child(key).removeValue();
                                    Toast.makeText(Admin_CG_Detail.this,"Record Deleted",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Admin_CG_Display.class));
                                    finish();

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

            }
        });*//*


    }

    private void showBookingDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_confirmbooking,null);
        final Calendar mCalendar = Calendar.getInstance();
        //final DatePicker date = findViewById(R.id.simpleDatePicker);
        final DatePicker date = (DatePicker) view.findViewById(R.id.simpleDatePicker);
        //date.setMaxDate(System.currentTimeMillis());
        final int minDay = 4;
        final int minMonth = 7;
        final int minYear = 2023;
        mCalendar.set(minYear, minMonth - 1, minDay);
        date.setMinDate(mCalendar.getTimeInMillis());

        Button confirm = view.findViewById(R.id.confirmbooking);

        AlertDialog.Builder builder = new AlertDialog.Builder(Client_RequestDetail.this);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateconfirm = (date.getDayOfMonth()+"/"+ (date.getMonth() + 1)+"/"+date.getYear());
//                int a = 1;
//                String i = String.valueOf(a);






                databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        rate = snapshot.child(detailTitle.getText().toString()).child("Rate").getValue(String.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Caregiver_UserName").setValue(detailTitle.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Caregiver_Name").setValue(detailName.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Date").setValue(dateconfirm);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Caregiver_Number").setValue(detailMobile.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Gig").setValue(detailGig.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Rate").setValue(rate);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Shift").setValue(detailTime.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Client_UserName").setValue(user);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Client_FullName").setValue(Fullname);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Client_PhoneNumber").setValue(Phone);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Client_Gender").setValue(Gender);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(String.valueOf(i)).child("Client_Address").setValue(location);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Caregiver_UserName").setValue(detailTitle.getText().toString());
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Caregiver_Name").setValue(detailName.getText().toString());
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Date").setValue(dateconfirm);
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Caregiver_Number").setValue(detailMobile.getText().toString());
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Gig").setValue(detailGig.getText().toString());
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Rate").setValue(rate);
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Shift").setValue(detailTime.getText().toString());
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Client_UserName").setValue(user);
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Client_FullName").setValue(Fullname);
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Client_PhoneNumber").setValue(Phone);
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Client_Gender").setValue(Gender);
                        databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Client_Address").setValue(location);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("Booking").child(String.valueOf(i)).child("Caregiver_UserName").setValue(detailTitle.getText().toString());
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Caregiver_Name").setValue(detailName.getText().toString());
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Date").setValue(dateconfirm);
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Caregiver_Number").setValue(detailMobile.getText().toString());
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Gig").setValue(detailGig.getText().toString());
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Rate").setValue(rate);
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Shift").setValue(detailTime.getText().toString());
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Client_UserName").setValue(user);
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Client_FullName").setValue(Fullname);
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Client_PhoneNumber").setValue(Phone);
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Client_Gender").setValue(Gender);
                        databaseReference.child("Booking").child(String.valueOf(i)).child("Client_Address").setValue(location);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //database booking
//                a++;
                i++;
                dialog.dismiss();
//                Intent i = new Intent(Client_CG_Detail.this, Client_ChooseCG.class);
//                startActivity(i);
//                finish();

            }
        });

*/

    }

}