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
import android.widget.ImageView;
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

public class CGSurvey extends AppCompatActivity {

    ProgressBar progressbar1,progressbar2;
    int counter = 0;

    Uri pdfUri; //these are URLs that are meant for local storage, Experience certificates
    Uri pdfUri1; //CNIC Docs
    String name;
    String user;
    ProgressDialog progressDialog;
    TextView Notification_Exp;
    TextView Notification_CNIC;
    TextView Notification_User;

    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");
    StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carefor-3f2dc.appspot.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cg_survey);



        progressbar1 = findViewById(R.id.progressbar1);
        progressbar2 = findViewById(R.id.progressbar2);
        final EditText Cnic = findViewById(R.id.cnic);
        final EditText Address = findViewById(R.id.address);
        final EditText Postal_code = findViewById(R.id.postal_code);
        final EditText Experience = findViewById(R.id.exp);
        final Button SelectExp_Button = findViewById(R.id.Select_Exp);
        final Button SelectCNIC_Button = findViewById(R.id.Select_CNIC);

        Notification_Exp = findViewById(R.id.Notify_Exp);
        Notification_CNIC = findViewById(R.id.Notify_CNIC);
        Notification_User = findViewById(R.id.Notify_User);


        final Button Survey_button = findViewById(R.id.Surveybutton);
        final ImageView Logout = findViewById(R.id.logout_survey);

        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);
        String pass = preferences.getString(KEY_PASSWORD,null);
        System.out.println("The UserName is: "+user);
        if (user != null || pass !=null){
            System.out.println("The UserName is: "+user);
        }

        Logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

//                Intent i = new Intent(CGSurvey.this, MainActivity.class);
//                startActivity(i);
                finish();
            }
        });

        SelectExp_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(CGSurvey.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    select_Exp_pdf();
                } else {
                    ActivityCompat.requestPermissions(CGSurvey.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        SelectCNIC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(CGSurvey.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    select_CNIC_pdf();
                } else {
                    ActivityCompat.requestPermissions(CGSurvey.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
                }
            }
        });

        Survey_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    final String CNIC = Cnic.getText().toString();
                    final String CG_Address = Address.getText().toString();
                    final String PostalCode = Postal_code.getText().toString();
                    final String CG_Exp = Experience.getText().toString();

                    if (CNIC.isEmpty()) {
                        Cnic.setError("Kindly Enter CNIC Number");
                        Cnic.requestFocus();
                    }else if (CG_Address.isEmpty()){
                        Address.setError("Kindly Enter Address");
                        Address.requestFocus();

                    }else if (PostalCode.isEmpty()){
                        Postal_code.setError("Kindly Enter Postal Code");
                        Postal_code.requestFocus();

                    }else if (CG_Exp.isEmpty()){
                        Experience.setError("Kindly Enter Experience");
                        Experience.requestFocus();

                    }else if (!CNIC.matches("^[0-9]{5}-[0-9]{7}-[0-9]{1}$")){
                        Cnic.setError("Kindly Enter Valid CNIC Number");  //CNIC Regex
                        Cnic.requestFocus();

                    }else if (!PostalCode.matches("^[0-9]{5}$")){
                        Postal_code.setError("Postal code should contain 5 numbers only"); //Postal Code Regex
                        Postal_code.requestFocus();

                    }else if (pdfUri==null){
                        Toast.makeText(CGSurvey.this, "Please Select a Certificate File",Toast.LENGTH_SHORT).show();

                    }else if (pdfUri1==null){
                        Toast.makeText(CGSurvey.this, "Please Select a CNIC File",Toast.LENGTH_SHORT).show();

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

                                        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                databaseReference.child("Caregiver").child(user).child("CNIC").setValue(CNIC);
                                                databaseReference.child("Caregiver").child(user).child("Address").setValue(CG_Address);
                                                databaseReference.child("Caregiver").child(user).child("Postal_Code").setValue(PostalCode);
                                                databaseReference.child("Caregiver").child(user).child("Experience").setValue(CG_Exp);
                                                databaseReference.child("Caregiver").child(user).child("Status").setValue("Approval Pending");
                                                upload_Exp_File(pdfUri);
                                                upload_CNIC_File(pdfUri1);

                                                Toast.makeText(CGSurvey.this, "Details Received", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(CGSurvey.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode ==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            select_Exp_pdf();
        }else if(requestCode ==10 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            select_CNIC_pdf();
        }else{
            Toast.makeText(CGSurvey.this, "Please Provide Permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void select_Exp_pdf() {
        //offer user to select a file. We will use Intent
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); //fetches files

        startActivityForResult(intent,86);

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
        if(requestCode==86 && resultCode==RESULT_OK && data!=null){
            pdfUri=data.getData(); //returns uri of selected file
           Notification_Exp.setText("A File for Certificate is Selected : "+ data.getData().getLastPathSegment());
            System.out.println("EXPERIENCE IS HEREEEE");
        }else if(requestCode==87 && resultCode==RESULT_OK && data!=null){
            pdfUri1=data.getData(); //returns uri of selected file
            Notification_CNIC.setText("A File for CNIC is Selected : "+ data.getData().getLastPathSegment());
            System.out.println("CNICCCCCCC IS HEREEEE");
        }else{
            Toast.makeText(CGSurvey.this,"Please Select a FIle",Toast.LENGTH_SHORT).show();
        }

    }
    private void upload_Exp_File(Uri pdfUri) {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = user;

        storage.child("Uploads").child("Experience").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        String url1 = uriTask.getResult().toString();
                        //String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(); //returns url of file
                        databaseReference.child("Caregiver").child(user).child("Experience_Doc").setValue(url1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(CGSurvey.this,"File Uploaded Successfully",Toast.LENGTH_SHORT);
                                    progressDialog.dismiss();
                                }else{
                                    Toast.makeText(CGSurvey.this,"File Not Uploaded",Toast.LENGTH_SHORT);
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CGSurvey.this,"File Not Uploaded",Toast.LENGTH_SHORT);

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                int currentProgress =(int) (100 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });

    }
    private void upload_CNIC_File(Uri pdfUri1) {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = user;

        storage.child("Uploads").child("CNIC").child(fileName).putFile(pdfUri1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        String url1 = uriTask.getResult().toString();
                        System.out.println("IDHER DEKHOOOOOOOOO:  " +url1);
                        //String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(); //returns url of file
                        databaseReference.child("Caregiver").child(user).child("CNIC_Doc").setValue(url1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(CGSurvey.this,"File Uploaded Successfully",Toast.LENGTH_SHORT);
                                    progressDialog.dismiss();
                                }else{
                                    Toast.makeText(CGSurvey.this,"File Not Uploaded",Toast.LENGTH_SHORT);
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CGSurvey.this,"File Not Uploaded",Toast.LENGTH_SHORT);

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                int currentProgress =(int) (100 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
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




}
