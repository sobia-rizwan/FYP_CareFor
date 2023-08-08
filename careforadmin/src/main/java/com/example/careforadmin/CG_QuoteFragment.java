package com.example.careforadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class CG_QuoteFragment extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_admin_cg_quote, container,false);


       final EditText Quote = root.findViewById(R.id.quote);
       final EditText Author = root.findViewById(R.id.author);
        final Button submit = root.findViewById(R.id.save);

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
                if (isConnected()) {
                    final String quote_CG = Quote.getText().toString();
                    final String author_CG = Author.getText().toString();

                    if (quote_CG.isEmpty()) {
                        Quote.setError("Kindly Enter Quote");
                        Quote.requestFocus();
                    } else if (author_CG.isEmpty()) {
                        Author.setError("Kindly Enter Quote");
                        Author.requestFocus();
                    } else {
                        ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Please wait..."); // Setting Message
                        progressDialog.setTitle("Saving Data"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        databaseReference.child("Quote").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild("caregiver")) {
                                    databaseReference.child("Quote").child("caregiver").child("quote").setValue(quote_CG);
                                    databaseReference.child("Quote").child("caregiver").child("author").setValue(author_CG);
                                    progressDialog.dismiss();

                                    Intent i = new Intent(getActivity(), QuoteActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getActivity(), "Error Occurred! Try Again Later", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }



}
