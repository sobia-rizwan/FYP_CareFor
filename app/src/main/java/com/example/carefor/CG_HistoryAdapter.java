package com.example.carefor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CG_HistoryAdapter extends RecyclerView.Adapter<CG_HistoryAdapter.ViewHolder> {

        private final Context context2;
        private final ArrayList<CG_HistoryModel> historyModelArrayList;

        // Constructor
        public CG_HistoryAdapter(Context context2, ArrayList<CG_HistoryModel> historyModelArrayList) {
            this.context2 = context2;
            this.historyModelArrayList = historyModelArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // to inflate the layout for each item of recycler view.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card_layout, parent, false);
            return new ViewHolder(view);
        }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        CG_HistoryModel model = historyModelArrayList.get(position);
        holder.Client_user.setText(model.getClient_username());
        holder.Appointment_type.setText(model.getAppointment_type());
        holder.Appointment_time.setText(model.getTiming());
        holder.Appointment_rate.setText(String.valueOf(model.getAppointment_rate()));
        holder.rating.setText(String.valueOf(model.getRatings()));


    }


        @Override
        public int getItemCount() {
            // this method is used for showing number of card items in recycler view
            return historyModelArrayList.size();
        }

        // View holder class for initializing of your views such as TextView and Imageview
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView Client_user;
            private final TextView Appointment_type;
            private final TextView Appointment_time;
            private final TextView Appointment_rate;
            private final TextView rating;





            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Client_user = itemView.findViewById(R.id.client);
                Appointment_type = itemView.findViewById(R.id.type);
                Appointment_time = itemView.findViewById(R.id.timing);
                Appointment_rate = itemView.findViewById(R.id.appointment_rate);
                rating = itemView.findViewById(R.id.Rating);


            }
        }
}
