package com.example.careforadmin;

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

public class ApprovalAdapter1 extends RecyclerView.Adapter<MyViewHolder3> {

    private Context context;
    private List<ApprovalClass> dataList;

    public ApprovalAdapter1(Context context, List<ApprovalClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item3,parent,false);
        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {

        holder.recTitle.setText(dataList.get(position).getUser_Name());
        holder.recStatus.setText(dataList.get(position).getStatus());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Admin_Client_SurveyDetail.class );

                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getUser_Name());
                intent.putExtra("Full Name", dataList.get(holder.getAdapterPosition()).getFull_Name());
                intent.putExtra("Status", dataList.get(holder.getAdapterPosition()).getStatus());
                intent.putExtra("CNIC", dataList.get(holder.getAdapterPosition()).getCNIC());
                intent.putExtra("Address", dataList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Experience", dataList.get(holder.getAdapterPosition()).getExperience());
                intent.putExtra("CNIC_Doc", dataList.get(holder.getAdapterPosition()).getCNIC_Doc());
               // intent.putExtra("Experience_Doc", dataList.get(holder.getAdapterPosition()).getExperience_Doc());
                intent.putExtra("key", dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void SearchDataList(ArrayList<ApprovalClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder3 extends RecyclerView.ViewHolder{

    TextView recTitle, recStatus;
    CardView recCard;

    public MyViewHolder3(@NonNull View itemView) {
        super(itemView);

        recCard= itemView.findViewById(R.id.recCard2);
        recTitle= itemView.findViewById(R.id.recTitle2);
        recStatus= itemView.findViewById(R.id.recStatus2);
    }
}
