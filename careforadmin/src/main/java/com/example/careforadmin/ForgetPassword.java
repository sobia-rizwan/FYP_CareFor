package com.example.careforadmin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetPassword extends AppCompatActivity {
    RadioGroup radioG;
    RadioButton radioB;
    ProgressBar progressbar1,progressbar2;
    int counter = 0;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        radioG = findViewById(R.id.radiog);
        progressbar1 = findViewById(R.id.progressbar1);
        progressbar2 = findViewById(R.id.progressbar2);
        final EditText username = findViewById(R.id.username);
        final EditText email_address = findViewById(R.id.email);
        final Button Recover = findViewById(R.id.forgetbutton);
        final TextView back_login = findViewById(R.id.loginPage);

        radioG.setTranslationX(-800);
        username.setTranslationX(-800);
        email_address.setTranslationX(-800);
        back_login.setTranslationX(-800);
        Recover.setTranslationX(-800);

        radioG.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email_address.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        back_login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Recover.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        back_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(ForgetPassword.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        Recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {


                    final String usern = username.getText().toString();
                    final String email = email_address.getText().toString();
                    int radioId = radioG.getCheckedRadioButtonId();
                    radioB = findViewById(radioId);
                    final String choice = radioB.getText().toString();

                    if (usern.isEmpty()) {
                        username.setError("Kindly Enter Email Address");
                        username.requestFocus();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        email_address.setError("Enter Valid Email Address");
                        email_address.requestFocus();
                    } else {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            progressbar1.setVisibility(View.VISIBLE);
                            progressbar2.setVisibility(View.VISIBLE);

                            Timer timer = new Timer();
                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    counter++;
                                    progressbar1.setProgress(counter);
                                    progressbar2.setProgress(counter);

                                    if (counter == 100) {
                                        timer.cancel();

                                        databaseReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                                                if (snapshot.hasChild(usern)) {
                                                    final String getEmail = snapshot.child(usern).child("Email_Address").getValue(String.class);
                                                    if (getEmail.equals(email)) {
                                                        firebaseAuth.sendPasswordResetEmail(getEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(ForgetPassword.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(ForgetPassword.this, MainActivity.class));
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(ForgetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        username.setText("");
                                                        email_address.setText("");
                                                        username.requestFocus();
                                                        progressbar1.setProgress(0);
                                                        progressbar1.setVisibility(View.INVISIBLE);
                                                        progressbar2.setProgress(0);
                                                        progressbar2.setVisibility(View.INVISIBLE);
                                                        Toast.makeText(ForgetPassword.this, "Wrong Email", Toast.LENGTH_LONG).show();
                                                    }

                                                } else {
                                                    username.setText("");
                                                    email_address.setText("");
                                                    username.requestFocus();
                                                    progressbar1.setProgress(0);
                                                    progressbar1.setVisibility(View.INVISIBLE);
                                                    progressbar2.setProgress(0);
                                                    progressbar2.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(ForgetPassword.this, "Account does not Exist", Toast.LENGTH_LONG).show();

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }

                                }
                            };
                            timer.schedule(timerTask, 100, 100);


                    }
                }else{
                    Toast.makeText(ForgetPassword.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(ForgetPassword.this, MainActivity.class);
        startActivity(i);
        finish();
    }




}
