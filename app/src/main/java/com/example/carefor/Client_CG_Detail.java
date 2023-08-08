package com.example.carefor;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Client_CG_Detail extends AppCompatActivity {

    //PAGE: activity_detail
    //activity_client_cg_detail this is not being used now but dont delete it. !!


    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    String user;
    String location;
    String Phone;
    String Fullname;
    String Gender;
    String rate;




    TextView detailTitle, detailName, detailCNIC, detailEmail, detailExp, detailClientG;
    TextView detailDesc, detailGig, detailRate, detailTime, detailAddress, detailMobile, detailPostal;
    ImageView BackBtn;
    Button Save_btn;
    String key = "";
    int i = 1;
    String a;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


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
        detailDesc = findViewById(R.id.descriptions);
        detailExp = findViewById(R.id.exps);
        detailEmail = findViewById(R.id.emails);
        detailAddress = findViewById(R.id.addresses);
        detailMobile = findViewById(R.id.mobile);
        detailPostal = findViewById(R.id.postal_codee);
        BackBtn = findViewById(R.id.back);
        Save_btn = findViewById(R.id.save_btn);


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            detailTitle.setText(bundle.getString("Title"));
            detailName.setText(bundle.getString("Full Name"));
            detailCNIC.setText(bundle.getString("CNIC"));
            detailEmail.setText(bundle.getString("Email"));
            detailExp.setText(bundle.getString("Experience"));
            //detailClientG.setText(bundle.getString("ClientG"));
            detailDesc.setText(bundle.getString("Description"));
            detailGig.setText(bundle.getString("Gig"));
            //detailRate.setText(bundle.getString("Rate"));
            detailTime.setText(bundle.getString("Time"));
            detailAddress.setText(bundle.getString("Address"));
            detailMobile.setText(bundle.getString("Phone Number"));
            detailPostal.setText(bundle.getString("Postal Code"));
            key = bundle.getString("key");
        }

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Client_CG_Detail.this, Client_ChooseCG.class);
                startActivity(i);
            }
        });

        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookingDialog();
            }
        });
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
        });*/

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

        AlertDialog.Builder builder = new AlertDialog.Builder(Client_CG_Detail.this);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateconfirm = (date.getDayOfMonth()+"/"+ (date.getMonth() + 1)+"/"+date.getYear());
//                int a = 1;
//                String i = String.valueOf(a);



                databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        location = snapshot.child(user).child("Address").getValue(String.class);
                        Phone = snapshot.child(user).child("Phone_Number").getValue(String.class);
                        Fullname = snapshot.child(user).child("Full_Name").getValue(String.class);
                        Gender = snapshot.child(user).child("Gender").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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

                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Caregiver_UserName").setValue(detailTitle.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Caregiver_Name").setValue(detailName.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Date").setValue(dateconfirm);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Caregiver_Number").setValue(detailMobile.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Gig").setValue(detailGig.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Rate").setValue(rate);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Shift").setValue(detailTime.getText().toString());
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Client_UserName").setValue(user);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Client_FullName").setValue(Fullname);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Client_PhoneNumber").setValue(Phone);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Client_Gender").setValue(Gender);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Client_Address").setValue(location);
                        databaseReference.child("Caregiver").child(detailTitle.getText().toString()).child("Booking").child(user).child("Booking_Status").setValue("Pending");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Caregiver_UserName").setValue(detailTitle.getText().toString());
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Caregiver_Name").setValue(detailName.getText().toString());
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Date").setValue(dateconfirm);
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Caregiver_Number").setValue(detailMobile.getText().toString());
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Gig").setValue(detailGig.getText().toString());
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Rate").setValue(rate);
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Shift").setValue(detailTime.getText().toString());
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Client_UserName").setValue(user);
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Client_FullName").setValue(Fullname);
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Client_PhoneNumber").setValue(Phone);
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Client_Gender").setValue(Gender);
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Client_Address").setValue(location);
                            databaseReference.child("Client").child(user).child("Booking").child(detailTitle.getText().toString()).child("Booking_Status").setValue("Pending");

                        /*databaseReference.child("Client").child(user).child("Booking").child(String.valueOf(i)).child("Caregiver_UserName").setValue(detailTitle.getText().toString());
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
*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String s=(new StringBuilder()).append(user).append(detailTitle.getText().toString()).toString();
                        System.out.println("BOOKING NAME: "+s);

                        databaseReference.child("Booking").child(s).child("Caregiver_UserName").setValue(detailTitle.getText().toString());
                        databaseReference.child("Booking").child(s).child("Caregiver_Name").setValue(detailName.getText().toString());
                        databaseReference.child("Booking").child(s).child("Date").setValue(dateconfirm);
                        databaseReference.child("Booking").child(s).child("Caregiver_Number").setValue(detailMobile.getText().toString());
                        databaseReference.child("Booking").child(s).child("Gig").setValue(detailGig.getText().toString());
                        databaseReference.child("Booking").child(s).child("Rate").setValue(rate);
                        databaseReference.child("Booking").child(s).child("Shift").setValue(detailTime.getText().toString());
                        databaseReference.child("Booking").child(s).child("Client_UserName").setValue(user);
                        databaseReference.child("Booking").child(s).child("Client_FullName").setValue(Fullname);
                        databaseReference.child("Booking").child(s).child("Client_PhoneNumber").setValue(Phone);
                        databaseReference.child("Booking").child(s).child("Client_Gender").setValue(Gender);
                        databaseReference.child("Booking").child(s).child("Client_Address").setValue(location);
                        databaseReference.child("Booking").child(s).child("Booking_Status").setValue("Pending");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //database booking
//                a++;
                i++;
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Booking Made", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Client_CG_Detail.this, ClientDashBoardActivity.class);
                startActivity(i);
                finish();

            }
        });


    }












}