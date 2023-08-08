package com.example.careforadmin;

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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class LoginTabFragment extends Fragment {


    ProgressBar progressbar1,progressbar2;
    int counter = 0;
    String status = "";

    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login, container,false);


        progressbar1 = root.findViewById(R.id.progressbar1);
        progressbar2 = root.findViewById(R.id.progressbar2);
        final EditText username = root.findViewById(R.id.username);
        final EditText password = root.findViewById(R.id.pass);
        final Button SignIn = root.findViewById(R.id.loginbutton);
        final TextView forgotpass = root.findViewById(R.id.forget_password);

        preferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String user = preferences.getString(KEY_USERNAME,null);

        /*if(user!=null){
            //if data is available directly call on HomeActivity
            databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(user).hasChild("Status")) {
                        status = snapshot.child(user).child("Status").getValue(String.class);
                        if (status.equals("Approved")) {
                            Intent i = new Intent(getActivity(), CGDashBoardActivity.class);
                            startActivity(i);
                        } else if (status.equals("Approval Pending")) {
                            Toast.makeText(getActivity(), "Your Approval is Pending", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), CGSurvey.class);
                            startActivity(i);
                        } else if (status.equals("Not Approved")) {
                            Toast.makeText(getActivity(), "Application Rejected", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), GoogleMapActivity.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getActivity(), GoogleMapActivity.class);
                            startActivity(i);
                        }
                    }else {
                        Intent i = new Intent(getActivity(), GoogleMapActivity.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }*/

        /*radioG.setTranslationX(-800);
        username.setTranslationX(-800);
        password.setTranslationX(-800);
        forgotpass.setTranslationX(-800);
        SignIn.setTranslationX(-800);

        radioG.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgotpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        SignIn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();*/

       /* forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
            }
        });
*/
            SignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected()) {
                        final String usern = username.getText().toString();
                        final String pass = password.getText().toString();
                        /*int radioId = radioG.getCheckedRadioButtonId();
                        radioB = root.findViewById(radioId);
                        final String choice = radioB.getText().toString();
*/
                        if (usern.isEmpty()) {
                            username.setError("Kindly Enter Username");
                            username.requestFocus();
//              }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                  emailaddress.setError("Enter Valid Email Address");
//                  emailaddress.requestFocus();
                        } else if (pass.isEmpty()) {
                            password.setError("Kindly Enter Password");
                            password.requestFocus();
                        } else {

                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        final String encryptpassword = md5(pass);

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
//                                                    final String getEmail = snapshot.child("").child("Email_Address").getValue(String.class);
//
                                                    //check if username exists in firebase database
                                                    final String username1 = snapshot.child("User_Name").getValue(String.class);
                                                    System.out.println("Username is: "+username1);
                                                    if(usern.equals(username1)){
                                                        final String pass1 = snapshot.child("Password").getValue(String.class);
                                                        System.out.println("Username is: "+pass1);
                                                        if(pass.equals(pass1)){
                                                            progressbar1.setProgress(0);
                                                            progressbar1.setVisibility(View.INVISIBLE);
                                                            progressbar2.setProgress(0);
                                                            progressbar2.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(getActivity(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString(KEY_USERNAME,usern);
                                                            editor.putString(KEY_PASSWORD,pass);
//                                                                    editor.putString("saved_name", usern);
//                                                                    editor.putString("saved_password", pass);
//                                                                    editor.putString("saved_choice", choice);
//                                                                    editor.commit();
                                                            editor.commit();
                                                            Intent i = new Intent(getActivity(),DashboardActivity.class);
                                                            startActivity(i);

                                                        }else{

                                                            username.setText("");
                                                            password.setText("");
                                                            username.requestFocus();
                                                            progressbar1.setProgress(0);
                                                            progressbar1.setVisibility(View.INVISIBLE);
                                                            progressbar2.setProgress(0);
                                                            progressbar2.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_LONG).show();
                                                            Intent i = new Intent(getActivity(),MainActivity.class);
                                                            startActivity(i);
                                                        }
                                                        //username exits in firebase
                                                        //now get password of user from firebase and match it with user entered password

//                                                                    progressbar1.setProgress(0);
//                                                                    progressbar1.setVisibility(View.INVISIBLE);
//                                                                    progressbar2.setProgress(0);
//                                                                    progressbar2.setVisibility(View.INVISIBLE);
//                                                                    Toast.makeText(getActivity(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
//                                                                    SharedPreferences.Editor editor = preferences.edit();
//                                                                    editor.putString(KEY_USERNAME,usern);
//                                                                    editor.putString(KEY_PASSWORD,pass);
////                                                                    editor.putString("saved_name", usern);
////                                                                    editor.putString("saved_password", pass);
////                                                                    editor.putString("saved_choice", choice);
////                                                                    editor.commit();
//                                                                    editor.commit();
//                                                                    Intent i = new Intent(getActivity(),DashboardActivity.class);
//                                                                    startActivity(i);


                                                                    /*if (status.equals("Approved")){
                                                                        Intent i = new Intent(getActivity(),CGDashBoardActivity.class);
                                                                        startActivity(i);
                                                                    }else if(status.equals("Approval Pending")){
                                                                        Toast.makeText(getActivity(), "Your Approval is Pending", Toast.LENGTH_SHORT).show();
                                                                        Intent i = new Intent(getActivity(),CGSurvey.class);
                                                                        startActivity(i);
                                                                    }else if(status.equals("Not Approved")){
                                                                        Toast.makeText(getActivity(), "Application Rejected", Toast.LENGTH_SHORT).show();
                                                                        Intent i = new Intent(getActivity(),GoogleMapActivity.class);
                                                                        startActivity(i);
                                                                    }else{
                                                                        Intent i = new Intent(getActivity(),GoogleMapActivity.class);
                                                                        startActivity(i);
                                                                    }
*/



                                                    /*Toast.makeText(getActivity(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getActivity(), CGDashBoardActivity.class));*/

                                                    } else {
                                                        username.setText("");
                                                        password.setText("");
                                                        username.requestFocus();
                                                        progressbar1.setProgress(0);
                                                        progressbar1.setVisibility(View.INVISIBLE);
                                                        progressbar2.setProgress(0);
                                                        progressbar2.setVisibility(View.INVISIBLE);
                                                        Toast.makeText(getActivity(), "Account does not Exist", Toast.LENGTH_LONG).show();
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


//                  databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                      @Override
//                      public void onDataChange(@NonNull DataSnapshot snapshot) {
//                          //check if username exists in firebase database
//                          if (snapshot.hasChild(email)){
//                              //username exits in firebase
//                              //now get password of user from firebase and match it with user entered password
//                              final String getPassword = snapshot.child(email).child("password").getValue(String.class);
//                              if(getPassword.equals()){
//                                  Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
//                                  startActivity(new Intent(LoginActivity.this,CGDashBoardActivity.class));
//                              }else{
//                                  Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
//                                  emailaddress.requestFocus();
//                              }
//
//                          }else {
//                              Toast.makeText(LoginActivity.this, "Account Doesnot Exist", Toast.LENGTH_SHORT).show();
//                                emailaddress.requestFocus();
//                          }
//                      }
//
//                      @Override
//                      public void onCancelled(@NonNull DatabaseError error) {
//
//                      }
//                  });
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

//
//    public String md5(String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


}
