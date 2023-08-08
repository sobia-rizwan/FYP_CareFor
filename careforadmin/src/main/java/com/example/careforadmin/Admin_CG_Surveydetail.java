package com.example.careforadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_CG_Surveydetail extends AppCompatActivity {

    TextView detailTitle, detailName, detailCNIC, detailExp, detailAddress;
    FloatingActionButton AcceptBtn, RejectBtn, StallBtn;
    Button ViewCNIC, ViewExp;
    String key = "";
    String CnicURL;
    String ExpURL, name;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cg_surveydetail);

        detailTitle = findViewById(R.id.detailTitle);
        detailName = findViewById(R.id.detailName);
        detailCNIC = findViewById(R.id.detailCNIC);
        detailAddress= findViewById(R.id.detailAddress);
        detailExp = findViewById(R.id.detailExperience);

        AcceptBtn = findViewById(R.id.AcceptBtn);
        RejectBtn = findViewById(R.id.RejectBtn);
        StallBtn = findViewById(R.id.StallBtn);

        ViewCNIC= findViewById(R.id.ViewCNIC);
        ViewExp= findViewById(R.id.ViewExp);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            detailTitle.setText(bundle.getString("Title"));
            detailName.setText(bundle.getString("Full Name"));
            detailCNIC.setText(bundle.getString("CNIC"));
            detailAddress.setText(bundle.getString("Address"));
            detailExp.setText(bundle.getString("Experience"));
            key = bundle.getString("key");
            CnicURL = bundle.getString("CNIC_Doc");
            ExpURL = bundle.getString("Experience_Doc");
            name = detailTitle.toString();
        }


        ViewCNIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(),WebActivity1.class);
                intent.putExtra("Cnic", CnicURL);
                startActivity(intent);*/

                downloadFile(Admin_CG_Surveydetail.this, name,"","", CnicURL);

            }
        });
        ViewExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(),WebActivity2.class);
                intent.putExtra("EXP",ExpURL);
                startActivity(intent);*/
                downloadFile(Admin_CG_Surveydetail.this, name,"","", ExpURL);

            }
        });

        AcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Caregiver");
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_CG_Surveydetail.this);
                builder.setMessage("Do you want to grant approval?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reference.child(key).child("Status").setValue("Approved");
                                Toast.makeText(Admin_CG_Surveydetail.this,"Status Updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin_CG_Approval.class));
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

        RejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 reference = FirebaseDatabase.getInstance().getReference("Caregiver");
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_CG_Surveydetail.this);
                builder.setMessage("Do you want to reject application?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reference.child(key).child("Status").setValue("Not Approved");
                                Toast.makeText(Admin_CG_Surveydetail.this,"Status Updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin_CG_Approval.class));
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

        StallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Caregiver");
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_CG_Surveydetail.this);
                builder.setMessage("Do you want to stall application?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reference.child(key).child("Status").setValue("Approval Pending");
                                Toast.makeText(Admin_CG_Surveydetail.this,"Status Updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin_CG_Approval.class));
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
    public long downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        return downloadmanager.enqueue(request);
    }
}
