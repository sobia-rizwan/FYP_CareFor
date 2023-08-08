package com.example.careforadmin;

import android.view.View;
import android.widget.TextView;

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
