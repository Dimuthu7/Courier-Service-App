package com.example.courier.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courier.R;
import com.example.courier.dto.FeedbackDto;

import java.util.ArrayList;

public class FeedbackDetAdapter extends RecyclerView.Adapter<FeedbackDetAdapter.feedbackViewHolder> {
    private ArrayList<FeedbackDto> feedbackList;

    public FeedbackDetAdapter(ArrayList<FeedbackDto> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public class feedbackViewHolder extends RecyclerView.ViewHolder{
        TextView tv_feedback, tv_order_id, tv_user_name;

        public feedbackViewHolder(final View view){
            super(view);
            tv_feedback = view.findViewById(R.id.tv_feedback);
            tv_order_id = view.findViewById(R.id.tv_order_id);
            tv_user_name = view.findViewById(R.id.tv_user_name);
        }
    }

    @NonNull
    @Override
    public FeedbackDetAdapter.feedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_feedback, parent, false);
        return new FeedbackDetAdapter.feedbackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackDetAdapter.feedbackViewHolder holder, int position) {
        holder.tv_feedback.setText(feedbackList.get(position).getFeedback());
        holder.tv_order_id.setText(""+ feedbackList.get(position).getOrderId());
        holder.tv_user_name.setText(feedbackList.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }
}
