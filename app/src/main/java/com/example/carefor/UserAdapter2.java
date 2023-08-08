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

public class UserAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {

    private Context context;
    private List<BookingClass> dataList;

    public UserAdapter2(Context context, List<BookingClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2,parent,false);
        return new MyViewHolder2(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

        holder.recTitle.setText(dataList.get(position).getClient_UserName());
        holder.recGender.setText(dataList.get(position).getClient_Gender());
        holder.recStatus.setText("View Status");

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CG_RequestDetail.class );

                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getClient_UserName());
                intent.putExtra("Full Name", dataList.get(holder.getAdapterPosition()).getClient_FullName());
                intent.putExtra("ClientG", dataList.get(holder.getAdapterPosition()).getClient_Gender());
                intent.putExtra("Rate", dataList.get(holder.getAdapterPosition()).getRate());
                intent.putExtra("Shift", dataList.get(holder.getAdapterPosition()).getShift());
                intent.putExtra("Gig", dataList.get(holder.getAdapterPosition()).getGig());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("Address", dataList.get(holder.getAdapterPosition()).getClient_Address());
                intent.putExtra("Phone Number", dataList.get(holder.getAdapterPosition()).getClient_PhoneNumber());
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

class MyViewHolder2 extends RecyclerView.ViewHolder{

    TextView recTitle, recStatus, recGender;
    CardView recCard;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);

        recCard= itemView.findViewById(R.id.recCard);
        recTitle= itemView.findViewById(R.id.recTitle);
        recStatus= itemView.findViewById(R.id.status);
        recGender= itemView.findViewById(R.id.recGender);

    }
}