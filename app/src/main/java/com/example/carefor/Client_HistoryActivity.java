package com.example.carefor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Client_HistoryActivity extends AppCompatActivity {


    SharedPreferences preferences;
    //so create a shared preferences name and also create key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    String user;

    RecyclerView RV;
    List<BookingClass> datalist;
    SearchView searchView;
    UserAdapter4 adapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history);

        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        user = preferences.getString(KEY_USERNAME,null);

        RV = findViewById(R.id.idRVcg);
        searchView = findViewById(R.id.Search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Client_HistoryActivity.this,1);
        RV.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(Client_HistoryActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        datalist = new ArrayList<>();
        adapter = new UserAdapter4(Client_HistoryActivity.this, datalist);
        RV.setAdapter(adapter);

        databaseReference.child("Client").child(user).child("Accepted_Booking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    BookingClass dataClass = dataSnapshot.getValue(BookingClass.class);
                    dataClass.setKey(dataSnapshot.getKey());
                    datalist.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }
    public void searchList(String text){
        ArrayList<BookingClass> searchList = new ArrayList<>();
        for (BookingClass dataClass: datalist){
            if(dataClass.getClient_UserName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.SearchDataList(searchList);
    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Client_HistoryActivity.this, ClientDashBoardActivity.class);
        startActivity(i);
        finish();
    }

}
