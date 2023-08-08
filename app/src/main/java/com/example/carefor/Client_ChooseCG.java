package com.example.carefor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Client_ChooseCG extends AppCompatActivity {

    //working:
    //DataClass mein DB se cheezain arhi hain. Iss class mein searching is done and checks if new data is in DB to usse change karta hai.
    // Adapter initialized and goes on adapter class with shit stored in dataclass
    //Adapter calls Client_CG_Detail udher sare getters se cheezain get hoti hain and shown as 'Details'


    RecyclerView RV;
    List<DataClass> datalist;
    SearchView searchView;
    UserAdapter adapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://carefor-3f2dc-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_cg_display);

        RV = findViewById(R.id.idRVcg);
        searchView = findViewById(R.id.Search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Client_ChooseCG.this,1);
        RV.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(Client_ChooseCG.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();



        datalist = new ArrayList<>();
        adapter = new UserAdapter(Client_ChooseCG.this, datalist);
        RV.setAdapter(adapter);

        databaseReference.child("Caregiver").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    dataClass.setKey(dataSnapshot.getKey());
                    datalist.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
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
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: datalist){
            if(dataClass.getUser_Name().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.SearchDataList(searchList);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Client_ChooseCG.this, ClientDashBoardActivity.class);
        startActivity(i);
        finish();
    }
}





       /* databaseReference.child("Caregiver").child("").child("GigSetup").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    datalist.add(dataClass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/



