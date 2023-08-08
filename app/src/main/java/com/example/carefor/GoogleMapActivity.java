package com.example.carefor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView
        .OnNavigationItemSelectedListener{
    MyListAdaper adapter;
    RecyclerView recyclerView;
    ClickListener listener;
    Button btFind;
    String saveloc = "";



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                location);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);

        TextView btexperienced = findViewById(R.id.experienced);

        btexperienced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GoogleMapActivity.this, CGSurvey.class);
                startActivity(i);
                finish();
            }
        });

        btFind = findViewById(R.id.bt_find);

//        List<MyListData> list = new ArrayList<>();
//        list = getData();

//        recyclerView
//                = (RecyclerView)findViewById(
//                R.id.recyclerView);
//        listener = new ClickListener() {
//
//            @Override
//            public void click(int index){
//                Toast.makeText(getApplicationContext(),"clicked item index is "+index,Toast.LENGTH_LONG).show();
//            }
//        };
//        adapter
//                = new MyListAdaper(list, getApplication(),listener);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(
//                new LinearLayoutManager(GoogleMapActivity.this));

        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveloc.equals("Dar-ul-Sukoon")){
                    List<MyListData> list = new ArrayList<>();
                    list = getData1();
                    recyclerView
                            = (RecyclerView)findViewById(
                            R.id.recyclerView);
                    listener = new ClickListener() {

                        @Override
                        public void click(int index){
                            if(index==0){
                                Toast.makeText(getApplicationContext(),"Launch Map",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("google.navigation:q=24.8763218,67.037035"));
                                Intent chooser = Intent.createChooser(intent,"Launch Map");
                                startActivity(chooser);

                            }else if(index==1){
                                Toast.makeText(getApplicationContext(),"Launch Map",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("google.navigation:q=24.8815123,67.0531295"));
                                Intent chooser = Intent.createChooser(intent,"Launch Map");
                                startActivity(chooser);

                            }
                            //Toast.makeText(getApplicationContext(),"clicked item index is "+index,Toast.LENGTH_LONG).show();
                        }
                    };
                    adapter
                            = new MyListAdaper(list, getApplication(),listener);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(
                            new LinearLayoutManager(GoogleMapActivity.this));

                }else if(saveloc.equals("KDSP")){

                    List<MyListData> list = new ArrayList<>();
                    list = getData2();

                    recyclerView
                            = (RecyclerView)findViewById(
                            R.id.recyclerView);
                    listener = new ClickListener() {

                        @Override
                        public void click(int index){
                            Toast.makeText(getApplicationContext(),"Launch Map",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("google.navigation:q=24.8634617,67.0685021"));
                            Intent chooser = Intent.createChooser(intent,"Launch Map");
                            startActivity(chooser);

                        }
                    };
                    adapter
                            = new MyListAdaper(list, getApplication(),listener);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(
                            new LinearLayoutManager(GoogleMapActivity.this));

                }else if(saveloc.equals("Edhi Foundation")){

                    List<MyListData> list = new ArrayList<>();
                    list = getData3();

                    recyclerView
                            = (RecyclerView)findViewById(
                            R.id.recyclerView);
                    listener = new ClickListener() {

                        @Override
                        public void click(int index){
                            Toast.makeText(getApplicationContext(),"Launch Map",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("google.navigation:q=24.8541405,67.0005801"));
                            Intent chooser = Intent.createChooser(intent,"Launch Map");
                            startActivity(chooser);
//                            Toast.makeText(getApplicationContext(),"clicked item index is "+index,Toast.LENGTH_LONG).show();
                        }
                    };
                    adapter
                            = new MyListAdaper(list, getApplication(),listener);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(
                            new LinearLayoutManager(GoogleMapActivity.this));

                }else{
                    Toast.makeText(getApplicationContext(),"Please Select from the list",Toast.LENGTH_LONG).show();
                }

            }

        }
        );

    }


    // create array of Strings
    // and store name of courses
    String[] location = { "Please Select","Dar-ul-Sukoon", "KDSP",
            "Edhi Foundation"};


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//        Toast.makeText(getApplicationContext(),
//                location[i],
//                Toast.LENGTH_LONG)
//                .show();
        saveloc = location[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(getApplicationContext(),
                "Please select a location",
                Toast.LENGTH_LONG)
                .show();

    }

    private List<MyListData> getData1()
    {
        List<MyListData> list = new ArrayList<>();
        list.add(new MyListData("Dar-ul-Sukun-A Home For The Senior Citizens",
                "House # JM-2/243, Catholic Colony # 1, M.A Jinnah Road, Opp. Quaid-E-Azam Mazzar, Karachi.",
                "+9221-32293248 | +9221-32293249 | +92 337 7778586 | +92 21-34558797"));
        list.add(new MyListData("Darul-ul-Sukoon - Head Office",
                "Address: 159/H/3 Kashmir Road, PECHS Karachi, Pakistan.",
                "+92 337 7778586 | +92 21-34558797 | +92 21 34558798 | +92 21 34558799"));
        return list;
    }

    private List<MyListData> getData2()
    {
        List<MyListData> list = new ArrayList<>();
        list.add(new MyListData("Karachi Down Syndrome Program (KDSP)",
                "KDSP Learning Center, Plot no 41/ E/ 1, Block 6, PECHS, Karachi",
                "+92-21-34395377 | +92-21-34315377 | +92-334-3355377"));
        return list;
    }

    private List<MyListData> getData3()
    {
        List<MyListData> list = new ArrayList<>();
        list.add(new MyListData("Edhi Foundation",
                "Sarafa Bazar, Boulton Market,Mithadar, Karachi.",
                "+92 (21) 32413232"));

        return list;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit CareFor?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        GoogleMapActivity.super.onBackPressed();
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
