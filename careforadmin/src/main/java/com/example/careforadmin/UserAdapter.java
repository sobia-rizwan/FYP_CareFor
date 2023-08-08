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

public class UserAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public UserAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recTitle.setText(dataList.get(position).getUser_Name());
        holder.recGender.setText(dataList.get(position).getGender());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Admin_CG_Detail.class );

                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getUser_Name());
                intent.putExtra("Full Name", dataList.get(holder.getAdapterPosition()).getFull_Name());
                intent.putExtra("CNIC", dataList.get(holder.getAdapterPosition()).getCNIC());
                intent.putExtra("Email", dataList.get(holder.getAdapterPosition()).getEmail_Address());
                intent.putExtra("Experience", dataList.get(holder.getAdapterPosition()).getExperience());
                intent.putExtra("ClientG", dataList.get(holder.getAdapterPosition()).getClientGender());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Gig", dataList.get(holder.getAdapterPosition()).getGigType());
                intent.putExtra("Rate", dataList.get(holder.getAdapterPosition()).getRate());
                intent.putExtra("Time", dataList.get(holder.getAdapterPosition()).getTimings());
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
class MyViewHolder extends RecyclerView.ViewHolder{

    TextView recTitle, recGender;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard= itemView.findViewById(R.id.recCard);
        recTitle= itemView.findViewById(R.id.recTitle);
        recGender= itemView.findViewById(R.id.recGender);
    }
}
