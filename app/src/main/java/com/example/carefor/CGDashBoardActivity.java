package com.example.carefor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CGDashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView Faq;
    ImageView Client_Review;
    ImageView New_request;
    ImageView History;
    String user;
    String pass;

    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgdash_board);

        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);
        pass = preferences.getString(KEY_PASSWORD,null);

        TextView usernameset = findViewById(R.id.User);
        usernameset.setText(user);

        ImageView editprofile = findViewById(R.id.editprofile);
        ImageView request = findViewById(R.id.requests);
        Faq=findViewById(R.id.faq);
        Client_Review= findViewById(R.id.clientreviews);
        New_request= findViewById(R.id.new_request);
        History= findViewById(R.id.gighistory);

        TextView quote = findViewById(R.id.editTextTextMultiLine);
        TextView author = findViewById(R.id.textView6);

        databaseReference.child("Quote").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getQuote = snapshot.child("caregiver").child("quote").getValue(String.class);
                final String getAuthor = snapshot.child("caregiver").child("author").getValue(String.class);
                System.out.println("Author is: "+getAuthor);
                System.out.println("QUote is: "+getQuote);
                quote.setText(getQuote);
                author.setText(getAuthor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CGDashBoardActivity.this, CG_EditProfile.class);
                startActivity(i);
                finish();
            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CGDashBoardActivity.this, CG_gigSetup.class);
                startActivity(i);
                finish();
            }
        });

        Faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CGDashBoardActivity.this, CG_FAQActivity.class);
                startActivity(i);
                finish();
            }
        });


        Client_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CGDashBoardActivity.this, CG_ReviewActivity.class);
                startActivity(i);
                finish();
            }
        });

        New_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CGDashBoardActivity.this, CG_RequestActivity.class);
                startActivity(i);
                finish();
            }
        });
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CGDashBoardActivity.this, CG_HistoryActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Hooks
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);

        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menuaction);

        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        { AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit CareFor?")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            finishAffinity();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_passwordchange:
                showChangePasswordDialog();
//                Intent intent = new Intent(CGDashBoardActivity.this,MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.nav_complaint:
                showReportDialog();
                break;
            case R.id.nav_about:
                showAboutUsDialog();
                break;
            case R.id.nav_logout:

                preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                user = preferences.getString(KEY_USERNAME,null);
                pass = preferences.getString(KEY_PASSWORD,null);

                if (user != null || pass !=null){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(CGDashBoardActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showChangePasswordDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_updatepass,null);

        final EditText oldpass = view.findViewById(R.id.oldpassword);
//        final EditText newpass = view.findViewById(R.id.newpassword);
//        final EditText retypepass = view.findViewById(R.id.retypepassword);
        Button changepass = view.findViewById(R.id.changepass);

        AlertDialog.Builder builder = new AlertDialog.Builder(CGDashBoardActivity.this);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()){

                    final String oldpassword = oldpass.getText().toString();
//                  final String newpassword = newpass.getText().toString();
//                  final String retypepassword = retypepass.getText().toString();

                    if(oldpassword.isEmpty()){
                        oldpass.setError("Enter Current Password");
                        oldpass.requestFocus();
//                  }else if(newpassword.isEmpty()){
//                      newpass.setError("Enter New Password");
//                      newpass.requestFocus();
//                  }else if (newpassword.length() < 6) {
//                      newpass.setError("Password length should be greater than 6 characters!");
//                      newpass.requestFocus();
//                  }else if(retypepassword.isEmpty()){
//                      retypepass.setError("Retype New Password");
//                      retypepass.requestFocus();
//                  }else if (!newpassword.equals(retypepassword)){
//                      retypepass.setError("New Password and Retype New Password should be same");
//                      retypepass.requestFocus();
                    }else{
                        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                        user = preferences.getString(KEY_USERNAME,null);
                        pass = preferences.getString(KEY_PASSWORD,null);

                        if (user != null || pass !=null) {

                            if (!oldpassword.equals(pass)){
                                Toast.makeText(getApplicationContext(),"Wrong Current Password",Toast.LENGTH_LONG).show();
                            }else{
                                dialog.dismiss();
                                updatepassword(oldpassword);
                            }
                        }
                    }

                }else {
                    Toast.makeText(CGDashBoardActivity.this,"No internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void updatepassword(String oldpassword) {

        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);
        pass = preferences.getString(KEY_PASSWORD,null);

        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getEmail = snapshot.child(user).child("Email_Address").getValue(String.class);
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                AuthCredential authCredential = EmailAuthProvider.getCredential(getEmail,oldpassword);
                FirebaseAuth.getInstance().sendPasswordResetEmail(getEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Password Change Email Sent!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showAboutUsDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_aboutus,null);
        final Button Close = view.findViewById(R.id.close);
        AlertDialog.Builder builder = new AlertDialog.Builder(CGDashBoardActivity.this);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showReportDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_report, null);
        final EditText title = view.findViewById(R.id.title);
        final EditText description = view.findViewById(R.id.report_description);
        final Button submit = view.findViewById(R.id.submit);

        AlertDialog.Builder builder = new AlertDialog.Builder(CGDashBoardActivity.this);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {

                    databaseReference.child("Report").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("Report").child(user).child("Username").setValue(user);
                            databaseReference.child("Report").child(user).child("Title").setValue(title.getText().toString());
                            databaseReference.child("Report").child(user).child("Description").setValue(description.getText().toString());

                            Toast.makeText(getApplicationContext(),"Report Sent",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Internet Connectivity Issue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) CGDashBoardActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

}