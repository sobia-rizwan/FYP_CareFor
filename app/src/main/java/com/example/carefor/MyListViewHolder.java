package com.example.carefor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

public class MyListViewHolder extends RecyclerView.ViewHolder {

    TextView Name;
    TextView Address;
    TextView Number;
    View view;

    MyListViewHolder(View itemView)
    {
        super(itemView);
        Name = (TextView)itemView
                .findViewById(R.id.Name);
        Address = (TextView)itemView
                .findViewById(R.id.Address);
        Number = (TextView)itemView
                .findViewById(R.id.Number);
        view  = itemView;
    }

}
