package com.example.carefor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Timer;
import java.util.TimerTask;

public class Client_EditProfile extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");
    String pass;

    ProgressBar progressbar1,progressbar2;
    int counter = 0;

    //these are URLs that are meant for local storage, Experience certificates
    Uri pdfUri1; //CNIC Docs
    String name;
    String user;
    ProgressDialog progressDialog;
    TextView Notification_CNIC;
    TextView Notification_User;

    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carefor-3f2dc.appspot.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_client_editprofile);

        progressbar1 = findViewById(R.id.progressbar1);
        progressbar2 = findViewById(R.id.progressbar2);
        final EditText Cnic = findViewById(R.id.cnic);
        final EditText Address = findViewById(R.id.address);
        final EditText Postal_code = findViewById(R.id.postal_code);
        final Button SelectCNIC_Button = findViewById(R.id.Select_CNIC);

        Notification_CNIC = findViewById(R.id.Notify_CNIC);
        Notification_User = findViewById(R.id.Notify_User);


        final Button Survey_button = findViewById(R.id.Surveybutton);


        preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME, null);
        pass = preferences.getString(KEY_PASSWORD, null);

        final TextView username = findViewById(R.id.user_name);
        final TextView fullname = findViewById(R.id.full_name);
        final TextView email = findViewById(R.id.email_address);
       // final TextView cnic = findViewById(R.id.usercnic);
        EditText phone = findViewById(R.id.phonenumber);
        /*EditText postal = findViewById(R.id.postalcode);
        EditText address = findViewById(R.id.address);
*/
        SelectCNIC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Client_EditProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    select_CNIC_pdf();
                } else {
                    ActivityCompat.requestPermissions(Client_EditProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
                }
            }
        });


        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getEmail = snapshot.child(user).child("Email_Address").getValue(String.class);
                final String getPhone = snapshot.child(user).child("Phone_Number").getValue(String.class);
                final String getFullname = snapshot.child(user).child("Full_Name").getValue(String.class);
                final String getUserName = snapshot.child(user).child("User_Name").getValue(String.class);

                username.setText(getUserName);
                fullname.setText(getFullname);
                email.setText(getEmail);
                phone.setText(getPhone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Survey_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    final String CNIC = Cnic.getText().toString();
                    final String CG_Address = Address.getText().toString();
                    final String PostalCode = Postal_code.getText().toString();
                    final String phone_number = phone.getText().toString();

                    if (CNIC.isEmpty()) {
                        Cnic.setError("Kindly Enter CNIC Number");
                        Cnic.requestFocus();
                    }else if (CG_Address.isEmpty()){
                        Address.setError("Kindly Enter Address");
                        Address.requestFocus();

                    }else if (PostalCode.isEmpty()){
                        Postal_code.setError("Kindly Enter Postal Code");
                        Postal_code.requestFocus();
                    } else if (phone_number.isEmpty()) {
                        phone.setError("Kindly Enter Phone Number");
                        phone.requestFocus();
                    }else if(!phone_number.matches("^03[0-9]{2}-[0-9]{7}$")){
                        phone.setError("Enter Valid Phone Number");
                        phone.requestFocus();
                    }else if (!CNIC.matches("^[0-9]{5}-[0-9]{7}-[0-9]{1}$")){
                        Cnic.setError("Kindly Enter Valid CNIC Number");  //CNIC Regex
                        Cnic.requestFocus();

                    }else if (!PostalCode.matches("^[0-9]{5}$")){
                        Postal_code.setError("Postal code should contain 5 numbers only"); //Postal Code Regex
                        Postal_code.requestFocus();

                    }else if (pdfUri1==null){
                        Toast.makeText(Client_EditProfile.this, "Please Select a CNIC File",Toast.LENGTH_SHORT).show();

                    }else {
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

                                    databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            databaseReference.child("Client").child(user).child("Phone_Number").setValue(phone_number);
                                            databaseReference.child("Client").child(user).child("CNIC").setValue(CNIC);
                                            databaseReference.child("Client").child(user).child("Address").setValue(CG_Address);
                                            databaseReference.child("Client").child(user).child("Postal_Code").setValue(PostalCode);
                                            databaseReference.child("Client").child(user).child("Status").setValue("Approval Pending");

                                            upload_CNIC_File(pdfUri1);

                                            Toast.makeText(Client_EditProfile.this, "Details Received", Toast.LENGTH_SHORT).show();

                                            Notification_User.setText("Dashboard Will be Locked Until Details Approved By Admin");

                                            progressbar1.setProgress(0);
                                            progressbar1.setVisibility(View.INVISIBLE);
                                            progressbar2.setProgress(0);
                                            progressbar2.setVisibility(View.INVISIBLE);


                                          /*      final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                                                if (snapshot.hasChild(usern)) {
                                                    final String getEmail = snapshot.child(usern).child("Email_Address").getValue(String.class);
                                                    if (getEmail.equals(email)) {
                                                        firebaseAuth.sendPasswordResetEmail(getEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(CGSurvey.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(CGSurvey.this, MainActivity.class));
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(CGSurvey.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        Cnic.setText("");
                                                        Address.setText("");
                                                        Postal_code.setText("");
                                                        Experience.setText("");
                                                        progressbar1.setProgress(0);
                                                        progressbar1.setVisibility(View.INVISIBLE);
                                                        progressbar2.setProgress(0);
                                                        progressbar2.setVisibility(View.INVISIBLE);
                                                        Toast.makeText(CGSurvey.this, "Wrong Email", Toast.LENGTH_LONG).show();
                                                    }

                                                } else {
                                                    Cnic.setText("");
                                                    Address.setText("");
                                                    Postal_code.setText("");
                                                    Experience.setText("");
                                                    progressbar1.setProgress(0);
                                                    progressbar1.setVisibility(View.INVISIBLE);
                                                    progressbar2.setProgress(0);
                                                    progressbar2.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(CGSurvey.this, "Account does not Exist", Toast.LENGTH_LONG).show();

                                                }*/
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
                    Toast.makeText(Client_EditProfile.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode ==10 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            select_CNIC_pdf();
        }else{
            Toast.makeText(Client_EditProfile.this, "Please Provide Permission", Toast.LENGTH_SHORT).show();
        }
    }


    private void select_CNIC_pdf() {
        //offer user to select a file. We will use Intent
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); //fetches files

        startActivityForResult(intent,87);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==87 && resultCode==RESULT_OK && data!=null){
            pdfUri1=data.getData(); //returns uri of selected file
            Notification_CNIC.setText("A File for CNIC is Selected : "+ data.getData().getLastPathSegment());
            System.out.println("CNICCCCCCC IS HEREEEE");
        }else{
            Toast.makeText(Client_EditProfile.this,"Please Select a FIle",Toast.LENGTH_SHORT).show();
        }

    }

    private void upload_CNIC_File(Uri pdfUri1) {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = user;

        storage.child("Uploads").child("Client").child("CNIC").child(fileName).putFile(pdfUri1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        String url1 = uriTask.getResult().toString();
                        // String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(); //returns url of file
                        databaseReference.child("Client").child(user).child("CNIC_Doc").setValue(url1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Client_EditProfile.this,"File Uploaded Successfully",Toast.LENGTH_SHORT);
                                    progressDialog.dismiss();
                                }else{
                                    Toast.makeText(Client_EditProfile.this,"File Not Uploaded",Toast.LENGTH_SHORT);
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Client_EditProfile.this,"File Not Uploaded",Toast.LENGTH_SHORT);

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                int currentProgress =(int) (100 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });

    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Client_EditProfile.this, ClientDashBoardActivity.class);
        startActivity(i);
        finish();
    }


}
