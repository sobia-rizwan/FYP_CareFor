package com.example.carefor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter1 extends RecyclerView.Adapter<MyViewHolder1> {

    private Context context;
    private List<DataClass> dataList;

    public UserAdapter1(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item1,parent,false);
        return new MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {

        holder.recTitle.setText(dataList.get(position).getUser_Name());
        holder.recStatus.setText("View Status");
        //System.out.println("BOOKING STATUS: "+ holder.recStatus.getText().toString());
        holder.recRate.setText(dataList.get(position).getRate());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Client_RequestDetail.class );

                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getUser_Name());
                intent.putExtra("Full Name", dataList.get(holder.getAdapterPosition()).getFull_Name());
                intent.putExtra("CNIC", dataList.get(holder.getAdapterPosition()).getCNIC());
                /*intent.putExtra("Email", dataList.get(holder.getAdapterPosition()).getEmail_Address());
                intent.putExtra("Experience", dataList.get(holder.getAdapterPosition()).getExperience());
                intent.putExtra("ClientG", dataList.get(holder.getAdapterPosition()).getClientGender());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDescription());
              */intent.putExtra("Gig", dataList.get(holder.getAdapterPosition()).getGigType());
                intent.putExtra("Rate", dataList.get(holder.getAdapterPosition()).getRate());
                intent.putExtra("Time", dataList.get(holder.getAdapterPosition()).getTimings());
               // intent.putExtra("Address", dataList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Phone Number", dataList.get(holder.getAdapterPosition()).getPhone_Number());
               //intent.putExtra("Booking Status", dataList.get(holder.getAdapterPosition()).getBooking_Status());

              //  intent.putExtra("Postal Code", dataList.get(holder.getAdapterPosition()).getPostal_Code());

                intent.putExtra("key", dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void SearchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder1 extends RecyclerView.ViewHolder{

    TextView recTitle, recStatus, recRate;
    CardView recCard;

    public MyViewHolder1(@NonNull View itemView) {
        super(itemView);

        recCard= itemView.findViewById(R.id.recCard);
        recTitle= itemView.findViewById(R.id.recTitle);
        recStatus= itemView.findViewById(R.id.status);
        recRate= itemView.findViewById(R.id.recRate);

    }
}
