package com.example.careforadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class MyListAdaper extends RecyclerView.Adapter<MyListViewHolder> {

    List<MyListData> list
            = Collections.emptyList();

    Context context;
    ClickListener listener;

    public MyListAdaper(List<MyListData> list,
                        Context context, ClickListener listener)
    {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyListViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType)
    {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.list_item,
                        parent, false);

        MyListViewHolder viewHolder
                = new MyListViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final MyListViewHolder viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.Name
                .setText(list.get(position).name);
        viewHolder.Address
                .setText(list.get(position).address);
        viewHolder.Number
                .setText(list.get(position).phonenumber);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listener.click(index);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
