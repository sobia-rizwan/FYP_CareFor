package com.example.carefor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CG_FAQAdapter extends RecyclerView.Adapter<CG_FAQAdapter.ViewHolder> {

        private final Context context;
        private final ArrayList<CG_FAQModel> faqModelArrayList;

        // Constructor
        public CG_FAQAdapter(Context context, ArrayList<CG_FAQModel> faqModelArrayList) {
            this.context = context;
            this.faqModelArrayList = faqModelArrayList;
        }

        @NonNull
        @Override
        public CG_FAQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // to inflate the layout for each item of recycler view.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        CG_FAQModel model = faqModelArrayList.get(position);
        holder.Question.setText(model.getQuestion());
        holder.Answer.setText(model.getAnswer());
    }


        @Override
        public int getItemCount() {
            // this method is used for showing number of card items in recycler view
            return faqModelArrayList.size();
        }

        // View holder class for initializing of your views such as TextView and Imageview
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView Question;
            private final TextView Answer;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Question = itemView.findViewById(R.id.FaqQuestion);
                Answer = itemView.findViewById(R.id.FaqAnswer);
            }
        }
}
