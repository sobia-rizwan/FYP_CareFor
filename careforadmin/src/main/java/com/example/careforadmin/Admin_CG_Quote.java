package com.example.careforadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_CG_Quote extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cg_quote);

        EditText Quote =findViewById(R.id.quote);
        EditText Author =findViewById(R.id.author);
        final Button submit = findViewById(R.id.save);

        databaseReference.child("Quote").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getQuote = snapshot.child("caregiver").child("quote").getValue(String.class);
                final String getAuthor = snapshot.child("caregiver").child("author").getValue(String.class);

                Quote.setText(getQuote);
                Author.setText(getAuthor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    final String quote_CG = Quote.getText().toString();
                    final String author_CG = Author.getText().toString();

                    if(quote_CG.isEmpty()){
                        Quote.setError("Kindly Enter Quote");
                        Quote.requestFocus();
                    }else if(author_CG.isEmpty()){
                        Author.setError("Kindly Enter Quote");
                        Author.requestFocus();
                    }else{
                        ProgressDialog progressDialog = new ProgressDialog(Admin_CG_Quote.this);
                        progressDialog.setMessage("Please wait..."); // Setting Message
                        progressDialog.setTitle("Saving Data"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        databaseReference.child("Quote").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild("caregiver")){
                                    databaseReference.child("Quote").child("caregiver").child("quote").setValue(quote_CG);
                                    databaseReference.child("Quote").child("caregiver").child("author").setValue(author_CG);
                                    progressDialog.dismiss();
                                    Intent i = new Intent(Admin_CG_Quote.this, Admin_CG_Quote.class);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Error Occurred! Try Again Later", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(Admin_CG_Quote.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }
}