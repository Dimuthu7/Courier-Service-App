package com.example.courier.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courier.dto.PendingOrderDetails;

import java.util.List;
import com.example.courier.R;
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetViewHolder> {
    Context mContext;
    List<PendingOrderDetails> mData;
    private OrderDetViewHolder.RecyclerViewClickListener clicklistener;

    public OrderDetailsAdapter(Context mContext, List<PendingOrderDetails> mData,
                               OrderDetViewHolder.RecyclerViewClickListener listener){
        this.mContext = mContext;
        this.mData = mData;
        this.clicklistener =listener;
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.OrderDetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.list_order_details,viewGroup,false);
        return new OrderDetViewHolder(layout,mContext,mData,clicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.OrderDetViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getReceiver_name());
        holder.tv_address_receive.setText(mData.get(position).getReceive_location());
        holder.tv_phone.setText(mData.get(position).getReceiver_mobile());
        holder.tv_desc.setText(mData.get(position).getDescription());

        String status = mData.get(position).getStatus();
        holder.tv_status.setText(status.equals("P") ? "Pending" :
                status.equals("A") ? "Approved" :
                        status.equals("S") ? "Picked" :
                                status.equals("O") ? "On The Way" :
                                        status.equals("D") ? "Delivered" : "Reject");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class OrderDetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name,tv_phone,tv_desc, tv_address_receive, tv_status;
        RecyclerViewClickListener mrecyclerViewClickListener;
        public OrderDetViewHolder(@NonNull View itemView, Context mContext, List<PendingOrderDetails> mData, RecyclerViewClickListener onClickListener) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address_receive = itemView.findViewById(R.id.tv_address_receive);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_status = itemView.findViewById(R.id.tv_status);
            this.mrecyclerViewClickListener = onClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mrecyclerViewClickListener.onClickListener(getAdapterPosition());

        }
        public interface RecyclerViewClickListener {
            void onClickListener(int position);
        }
    }
}
