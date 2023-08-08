package com.example.carefor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CG_ReviewAdapter extends RecyclerView.Adapter<CG_ReviewAdapter.ViewHolder> {

        private final Context context1;
        private final ArrayList<CG_ReviewModel> reviewModelArrayList;

        // Constructor
        public CG_ReviewAdapter(Context context1, ArrayList<CG_ReviewModel> reviewModelArrayList) {
            this.context1 = context1;
            this.reviewModelArrayList = reviewModelArrayList;
        }

        @NonNull
        @Override
        public CG_ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // to inflate the layout for each item of recycler view.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card_layout, parent, false);
            return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        CG_ReviewModel model = reviewModelArrayList.get(position);
        holder.Appointment.setText(model.getAppointment());
        holder.Rating.setText(String.valueOf(model.getRating()));
        holder.Feedback.setText(model.getFeedback());
    }


        @Override
        public int getItemCount() {
            // this method is used for showing number of card items in recycler view
            return reviewModelArrayList.size();
        }

        // View holder class for initializing of your views such as TextView and Imageview
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView Appointment;
            private final TextView Rating;
            private final TextView Feedback;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Appointment = itemView.findViewById(R.id.Appointment);
                Rating = itemView.findViewById(R.id.Rating);
                Feedback = itemView.findViewById(R.id.Feedback);
            }
        }
}
