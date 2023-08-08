package com.example.carefor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CG_FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgfaq);
        RecyclerView FaqRV = findViewById(R.id.idRVFaq);

        // Here, we have created new array list and added data to it
        ArrayList<CG_FAQModel> FaqModelArrayList = new ArrayList<CG_FAQModel>();

        FaqModelArrayList.add(new CG_FAQModel("Question 1: As a Caregiver, how can I contact Support? ","Answer 1: Please, contact us at Carefor-Support@gmail.com  "));
        FaqModelArrayList.add(new CG_FAQModel("Question 2: Will I be a CareFor Employee, if I become a Caregiver? ","Answer 2: You will not become a CareFor employee as the legal employment status will not fall under CareFor. But you will always be a part of the CareFor Family as our Caregiver "));
        FaqModelArrayList.add(new CG_FAQModel("Question 3: Can I share my account with others? ","Answer 3: Another person using your account poses a serious safety concern. If this is flagged to us, the account will be immediately terminated. "));
        FaqModelArrayList.add(new CG_FAQModel("Question 4: Can I bring someone with me while I am online/on duty? ","Answer 4: "));
        FaqModelArrayList.add(new CG_FAQModel("Question 5: Can I use CareFor with any mobile phone? ","Answer 5: For safety and comfort, when Caregivers are on duty with CareFor, they are in no way allowed to be accompanied by anyone "));
        /*FaqModelArrayList.add(new CG_FAQModel("Question 6:  ","Answer 6: "));
        FaqModelArrayList.add(new CG_FAQModel("Question 7: ","Answer 7: "));
        FaqModelArrayList.add(new CG_FAQModel("Question 8: ","Answer 8: "));
        FaqModelArrayList.add(new CG_FAQModel("Question 9: ","Answer 9: "));
        FaqModelArrayList.add(new CG_FAQModel("Question 10: ","Answer 10: "));*/
        // we are initializing our adapter class and passing our arraylist to it.
        CG_FAQAdapter faqAdapter = new CG_FAQAdapter(this, FaqModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        FaqRV.setLayoutManager(linearLayoutManager);
        FaqRV.setAdapter(faqAdapter);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(CG_FAQActivity.this, CGDashBoardActivity.class);
        startActivity(i);
        finish();
    }



}
