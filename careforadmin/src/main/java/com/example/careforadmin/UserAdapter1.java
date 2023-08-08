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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2,parent,false);
        return new MyViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {

        holder.recTitle.setText(dataList.get(position).getUser_Name());
        holder.recGender.setText(dataList.get(position).getGender());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Admin_Client_Detail.class );

                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getUser_Name());
                intent.putExtra("Full Name", dataList.get(holder.getAdapterPosition()).getFull_Name());
                intent.putExtra("CNIC", dataList.get(holder.getAdapterPosition()).getCNIC());
                intent.putExtra("Email", dataList.get(holder.getAdapterPosition()).getEmail_Address());
               /* intent.putExtra("Experience", dataList.get(holder.getAdapterPosition()).getExperience());
                intent.putExtra("ClientG", dataList.get(holder.getAdapterPosition()).getClientGender());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Gig", dataList.get(holder.getAdapterPosition()).getGigType());
                intent.putExtra("Rate", dataList.get(holder.getAdapterPosition()).getRate());
                intent.putExtra("Time", dataList.get(holder.getAdapterPosition()).getTimings());*/
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

    TextView recTitle, recGender;
    CardView recCard;

    public MyViewHolder1(@NonNull View itemView) {
        super(itemView);

        recCard= itemView.findViewById(R.id.recCard1);
        recTitle= itemView.findViewById(R.id.recTitle1);
        recGender= itemView.findViewById(R.id.recGender1);
    }
}
