package com.example.carefor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");


    private static int Splash_timeout = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashintent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(splashintent);
                finish();
                if (isConnected()) {
                    preferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    String user = preferences.getString(KEY_USERNAME, null);

                    if (user != null) {
                        //if data is available directly call on HomeActivity
                        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child(user).getRef().getParent().equals("Caregiver")) {
                                    if (snapshot.child(user).hasChild("Status")) {
                                        final String status = snapshot.child(user).child("Status").getValue(String.class);
                                        if (status.equals("Approved")) {
                                            Intent i = new Intent(SplashActivity.this, CGDashBoardActivity.class);
                                            startActivity(i);
                                        } else if (status.equals("Approval Pending")) {
                                            Toast.makeText(SplashActivity.this, "Your Approval is Pending", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(SplashActivity.this, CGSurvey.class);
                                            startActivity(i);
                                        } else if (status.equals("Not Approved")) {
                                            Toast.makeText(SplashActivity.this, "Application Rejected", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(SplashActivity.this, GoogleMapActivity.class);
                                            startActivity(i);
                                        } else {
                                            Intent i = new Intent(SplashActivity.this, GoogleMapActivity.class);
                                            startActivity(i);
                                        }
                                    } else {
                                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                }else{

                                    databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.child(user).exists()) {
                                                if (snapshot.child(user).hasChild("Status")) {
                                                    final String status = snapshot.child(user).child("Status").getValue(String.class);
                                                    if (status.equals("Approved")) {
                                                        Intent i = new Intent(SplashActivity.this, ClientDashBoardActivity.class);
                                                        startActivity(i);
                                                    } else if (status.equals("Approval Pending")) {
                                                        Toast.makeText(SplashActivity.this, "Your Approval is Pending", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(SplashActivity.this, ClientSurvey.class);
                                                        startActivity(i);
                                                    } /*else if (status.equals("Not Approved")) {
                                                        Toast.makeText(SplashActivity.this, "Application Rejected", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(SplashActivity.this, GoogleMapActivity.class);
                                                        startActivity(i);
                                                    }*/ else {
                                                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                                        startActivity(i);
                                                    }
                                                } else {
                                                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                                    startActivity(i);
                                                }
                                            }else{
                                                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                                startActivity(i);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        }else {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                    }


                }else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        },Splash_timeout);


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




}
