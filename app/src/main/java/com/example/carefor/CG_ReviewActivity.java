package com.example.carefor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CG_ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgreviews);
        RecyclerView ReviewRV = findViewById(R.id.idRVReview);

        // Here, we have created new array list and added data to it
        ArrayList<CG_ReviewModel> reviewModelArrayList = new ArrayList<CG_ReviewModel>();

        reviewModelArrayList.add(new CG_ReviewModel("Appointment Type: Home Care",1,"Feedback: Caregiver did not know how to handle the patient"));
        reviewModelArrayList.add(new CG_ReviewModel("Appointment Type: Social Activity",2, "Feedback: Will not refer to anyone"));
        reviewModelArrayList.add(new CG_ReviewModel("Appointment Type: Medical Appointment",3, "Feedback: Wonderful Job"));
        reviewModelArrayList.add(new CG_ReviewModel("Appointment Type: Home Care",5, "Feedback: Amazingly calm"));
        reviewModelArrayList.add(new CG_ReviewModel("Appointment Type: Social Activity",5, "Feedback: Caregiver was extremely nice did a wonderful job"));
        /*reviewModelArrayList.add(new CG_ReviewModel("Question 3: Can I share my account with others? ",3, ""));
        reviewModelArrayList.add(new CG_ReviewModel("Question 4: Can I bring someone with me while I am online/on duty? ",5, ""));
        reviewModelArrayList.add(new CG_ReviewModel("Question 5: Can I use CareFor with any mobile phone? ",5, ""));
*/
        // we are initializing our adapter class and passing our arraylist to it.
        CG_ReviewAdapter reviewAdapter = new CG_ReviewAdapter(this, reviewModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        ReviewRV.setLayoutManager(linearLayoutManager);
        ReviewRV.setAdapter(reviewAdapter);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(CG_ReviewActivity.this, CGDashBoardActivity.class);
        startActivity(i);
        finish();
    }



}
