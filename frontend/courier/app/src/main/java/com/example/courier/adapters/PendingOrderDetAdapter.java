package com.example.courier.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courier.dto.PendingOrderDetails;

import java.util.ArrayList;
import com.example.courier.R;

public class PendingOrderDetAdapter extends RecyclerView.Adapter<PendingOrderDetAdapter.OrderDetailsViewHolder> {
    private ArrayList<PendingOrderDetails> orderDetails;
    private RecyclerViewClickListener listener;

    public PendingOrderDetAdapter(ArrayList<PendingOrderDetails> orderDetails, RecyclerViewClickListener listener){
        this.orderDetails = orderDetails;
        this.listener = listener;
    }

    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView orderId;
        private TextView description;
        private TextView pickupLocation;
        private TextView receiveLocation;

        public OrderDetailsViewHolder(final View view){
            super(view);
            orderId = view.findViewById(R.id.textView_orderId);
            description = view.findViewById(R.id.textView_description);
            pickupLocation = view.findViewById(R.id.textView_pickup_loc);
            receiveLocation = view.findViewById(R.id.textView_receive_loc);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public PendingOrderDetAdapter.OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pending_orders, parent,false);
        return new OrderDetailsViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrderDetAdapter.OrderDetailsViewHolder holder, int position) {
        String order_id = orderDetails.get(position).getOrder_id();
        String description = orderDetails.get(position).getDescription();
        String pickup_location = orderDetails.get(position).getPickup_location();
        String receive_location = orderDetails.get(position).getReceive_location();

        holder.orderId.setText(order_id);
        holder.description.setText(description);
        holder.pickupLocation.setText(pickup_location);
        holder.receiveLocation.setText(receive_location);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
