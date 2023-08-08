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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CG_EditProfile extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");
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
        setContentView(R.layout.actvity_cgeditprofile);


        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);
        pass = preferences.getString(KEY_PASSWORD,null);

        final TextView username = findViewById(R.id.user_name);
        final TextView fullname = findViewById(R.id.full_name);
        final TextView email = findViewById(R.id.email_address);
        final TextView cnic = findViewById(R.id.usercnic);
        EditText phone =findViewById(R.id.phonenumber);
        EditText postal = findViewById(R.id.postalcode);
        EditText address = findViewById(R.id.address);

        final Button submit = findViewById(R.id.save);



        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getCNIC =snapshot.child(user).child("CNIC").getValue(String.class);
                final String getEmail = snapshot.child(user).child("Email_Address").getValue(String.class);
                final String getAddress = snapshot.child(user).child("Address").getValue(String.class);
                final String getPhone = snapshot.child(user).child("Phone_Number").getValue(String.class);
                final String getFullname = snapshot.child(user).child("Full_Name").getValue(String.class);
                final String getUserName = snapshot.child(user).child("User_Name").getValue(String.class);
                final String getPostalCode = snapshot.child(user).child("Postal_Code").getValue(String.class);

                username.setText(getUserName);
                fullname.setText(getFullname);
                email.setText(getEmail);
                cnic.setText(getCNIC);
                phone.setText(getPhone);
                postal.setText(getPostalCode);
                address.setText(getAddress);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user!=null){
                    if (isConnected()) {
                        final String home_address = address.getText().toString();
                        final String postal_code = postal.getText().toString();
                        final String phone_number = phone.getText().toString();
                        if (home_address.isEmpty()) {
                            address.setError("Kindly Enter Home Address");
                            address.requestFocus();
                        } else if (postal_code.isEmpty()) {
                            postal.setError("Kindly Enter Postal Code");
                            postal.requestFocus();
                        } else if (phone_number.isEmpty()) {
                            phone.setError("Kindly Enter Phone Number");
                            phone.requestFocus();
                        } else if(!postal_code.matches("^[0-9]{5}$")){
                            postal.setError("Enter Valid postal code");
                            postal.requestFocus();
                        }else if(!phone_number.matches("^03[0-9]{2}-[0-9]{7}$")){
                            phone.setError("Enter Valid Phone Number");
                            phone.requestFocus();
                        }
                        else {

                            ProgressDialog progressDialog = new ProgressDialog(CG_EditProfile.this);
                            progressDialog.setMessage("Please wait..."); // Setting Message
                            progressDialog.setTitle("Saving Data"); // Setting Title
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                            progressDialog.show(); // Display Progress Dialog
                            databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //check if username exists in firebase database
                                    if (snapshot.hasChild(user)) {
                                        databaseReference.child("Caregiver").child(user).child("Phone_Number").setValue(phone_number);
                                        databaseReference.child("Caregiver").child(user).child("Address").setValue(home_address);
                                        databaseReference.child("Caregiver").child(user).child("Postal_Code").setValue(postal_code);

                                        progressDialog.dismiss();
                                        Intent i = new Intent(CG_EditProfile.this, CG_EditProfile.class);
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
        Intent i = new Intent(CG_EditProfile.this, CGDashBoardActivity.class);
        startActivity(i);
        finish();
    }


}
