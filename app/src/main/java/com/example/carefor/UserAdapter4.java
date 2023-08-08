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

public class UserAdapter4 extends RecyclerView.Adapter<MyViewHolder4>{

    private Context context;
    private List<BookingClass> dataList;

    public UserAdapter4(Context context, List<BookingClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder4 holder, int position) {
        holder.recTitle.setText(dataList.get(position).getClient_UserName());
        holder.recGender.setText("View Status");
        holder.recRate.setText(dataList.get(position).getRate());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Client_HistoryDetail.class );

                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getCaregiver_UserName());
                intent.putExtra("Full Name", dataList.get(holder.getAdapterPosition()).getCaregiver_Name());
                intent.putExtra("ClientG", dataList.get(holder.getAdapterPosition()).getClient_Gender());
                intent.putExtra("Rate", dataList.get(holder.getAdapterPosition()).getRate());
                intent.putExtra("Shift", dataList.get(holder.getAdapterPosition()).getShift());
                intent.putExtra("Gig", dataList.get(holder.getAdapterPosition()).getGig());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("Address", dataList.get(holder.getAdapterPosition()).getClient_Address());
                intent.putExtra("Phone Number", dataList.get(holder.getAdapterPosition()).getCaregiver_Number());
                intent.putExtra("Booking Status", dataList.get(holder.getAdapterPosition()).getBooking_Status());
                intent.putExtra("key", dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void SearchDataList(ArrayList<BookingClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder4 extends RecyclerView.ViewHolder{

    TextView recTitle, recRate, recGender;
    CardView recCard;

    public MyViewHolder4(@NonNull View itemView) {
        super(itemView);

        recCard= itemView.findViewById(R.id.recCard);
        recTitle= itemView.findViewById(R.id.recTitle);
        recRate= itemView.findViewById(R.id.recRate);
        recGender= itemView.findViewById(R.id.recGender);

    }
}