package com.example.carefor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class CG_gigSetup extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");
    String time="";
    String user;
    String pass;

    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cg_setup);

        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);
        pass = preferences.getString(KEY_PASSWORD,null);

        final RadioGroup radiopref = findViewById(R.id.radiopref);
        final RadioGroup radiogender = findViewById(R.id.radiog);
        final EditText describe = findViewById(R.id.describe);
        final RadioGroup radiogig = findViewById(R.id.radio_pref);
        final EditText rate = findViewById(R.id.pkr);
        final Button submit = findViewById(R.id.Setup_button);
        final RadioButton mgender = findViewById(R.id.maleradio);
        final RadioButton fgender = findViewById(R.id.femaleradio);
        final RadioButton home = findViewById(R.id.HomeRadio);
        final RadioButton medical = findViewById(R.id.MedRadio);
        final RadioButton social = findViewById(R.id.SocialRadio);
        final RadioButton day = findViewById(R.id.dayradio);
        final RadioButton full = findViewById(R.id.fullradio);

/*

        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getGender =snapshot.child(user).child("ClientGender").getValue(String.class);
                final String getDescription = snapshot.child(user).child("Description").getValue(String.class);
                final String getPref = snapshot.child(user).child("GigType").getValue(String.class);
                final String getTimings = snapshot.child(user).child("Timings").getValue(String.class);
                final String getRate = snapshot.child(user).child("Rate").getValue(String.class);

                if(getGender.equals("Male")){
                    int gender = mgender.getId();
                    radiogender.check(gender);
                }else if(getGender.equals("Female")){
                    int gender = fgender.getId();
                    radiogender.check(gender);
                }else{
                    radiogender.clearCheck();
                }
                if (getPref.equals("Home Care")){
                    int pref = home.getId();
                    radiogig.check(pref);
                }else if(getPref.equalsIgnoreCase("Medical Appointment")){
                    int pref = medical.getId();
                    radiogig.check(pref);
                }else if(getPref.equalsIgnoreCase("Social Activity")){
                    int pref = social.getId();
                    radiogig.check(pref);
                }else{
                    radiogig.clearCheck();
                }
                if(getTimings.equalsIgnoreCase("Day time")){
                    int pref = day.getId();
                    radiopref.check(pref);
                }else if(getTimings.equalsIgnoreCase("Full-time (24hr)")){
                    int pref = full.getId();
                    radiopref.check(pref);
                }else {
                    radiopref.clearCheck();
                }

                describe.setText(getDescription);
                rate.setText(getRate);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user!=null){
                    if (isConnected()) {
                        final String info = describe.getText().toString();
                        final String pkr = rate.getText().toString();
                        int radioId = radiogender.getCheckedRadioButtonId();
                        RadioButton radioB = findViewById(radioId);
                        final String gender = radioB.getText().toString();
                        int radiotimeid = radiopref.getCheckedRadioButtonId();
                        RadioButton radiopref = findViewById(radiotimeid);
                        final String timings = radiopref.getText().toString();
                        int radio = radiogig.getCheckedRadioButtonId();
                        RadioButton radioButton = findViewById(radio);
                        final String gig = radioButton.getText().toString();

                        if (info.isEmpty()) {
                            describe.setError("Kindly Enter Description");
                            describe.requestFocus();
                        } else if (pkr.isEmpty()) {
                            rate.setError("Kindly Enter Password");
                            rate.requestFocus();
                        } else if(time.equals("Please Select")){
                            Toast.makeText(getApplicationContext(),"Please Select Preferred Timings",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            ProgressDialog progressDialog = new ProgressDialog(CG_gigSetup.this);
                            progressDialog.setMessage("Please wait..."); // Setting Message
                            progressDialog.setTitle("Saving Data"); // Setting Title
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                            progressDialog.show(); // Display Progress Dialog
                            databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    final String getEmail = snapshot.child(user).child("Email_Address").getValue(String.class);
                                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                                    //check if username exists in firebase database
                                    if (snapshot.hasChild(user)) {
                                        databaseReference.child("Caregiver").child(user).child("ClientGender").setValue(gender);
                                        databaseReference.child("Caregiver").child(user).child("GigType").setValue(gig);
                                        databaseReference.child("Caregiver").child(user).child("Description").setValue(info);
                                        databaseReference.child("Caregiver").child(user).child("Rate").setValue(pkr);
                                        databaseReference.child("Caregiver").child(user).child("Timings").setValue(timings);
                                        progressDialog.dismiss();
                                        Intent i = new Intent(CG_gigSetup.this, CGDashBoardActivity.class);
                                        startActivity(i);
                                        finish();

                                                    /*Toast.makeText(getActivity(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getActivity(), CGDashBoardActivity.class));*/

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error Occurred! Try Again Later", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(CG_gigSetup.this, CGDashBoardActivity.class);
        startActivity(i);
        finish();
    }


}