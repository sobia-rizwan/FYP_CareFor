package com.example.careforadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_CG_Detail extends AppCompatActivity {

    TextView detailTitle, detailName, detailCNIC, detailEmail, detailExp, detailClientG;
    TextView detailDesc, detailGig, detailRate, detailTime;
    FloatingActionButton deleteButton;
    String key = "";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cg_detail);

        detailTitle = findViewById(R.id.detailTitle);
        detailName = findViewById(R.id.detailName);
        detailCNIC = findViewById(R.id.detailCNIC);
        detailEmail= findViewById(R.id.detailEmail);
        detailExp = findViewById(R.id.detailEXP);
        detailClientG= findViewById(R.id.detailClientGender);
        detailDesc = findViewById(R.id.detailDescription);
        detailGig = findViewById(R.id.detailGIG);
        detailRate = findViewById(R.id.detailRate);
        detailTime = findViewById(R.id.detailTime);
        deleteButton = findViewById(R.id.deleteButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            detailTitle.setText(bundle.getString("Title"));
            detailName.setText(bundle.getString("Full Name"));
            detailCNIC.setText(bundle.getString("CNIC"));
            detailEmail.setText(bundle.getString("Email"));
            detailExp.setText(bundle.getString("Experience"));
            detailClientG.setText(bundle.getString("ClientG"));
            detailDesc.setText(bundle.getString("Description"));
            detailGig.setText(bundle.getString("Gig"));
            detailRate.setText(bundle.getString("Rate"));
            detailTime.setText(bundle.getString("Time"));
            key = bundle.getString("key");
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Caregiver");
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_CG_Detail.this);
                    builder.setMessage("Do you want to delete this record?")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    reference.child(key).removeValue();
                                    Toast.makeText(Admin_CG_Detail.this,"Record Deleted",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Admin_CG_Display.class));
                                    finish();

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

            }
        });

    }
}