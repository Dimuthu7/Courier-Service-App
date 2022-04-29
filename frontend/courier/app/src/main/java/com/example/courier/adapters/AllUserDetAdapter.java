package com.example.courier.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courier.Models.ResAdminAllUserInfoModel;
import com.example.courier.R;

import java.util.ArrayList;

public class AllUserDetAdapter extends RecyclerView.Adapter<AllUserDetAdapter.allUserViewHolder> {
    private ArrayList<ResAdminAllUserInfoModel> userList;

    public AllUserDetAdapter(ArrayList<ResAdminAllUserInfoModel> userList) {
        this.userList = userList;
    }

    public class allUserViewHolder extends RecyclerView.ViewHolder{
        TextView list_item_driver_name,list_item_driver_status,list_item_driver_id;

        public allUserViewHolder(final View view){
            super(view);
            list_item_driver_name = view.findViewById(R.id.list_item_driver_name);
            list_item_driver_status = view.findViewById(R.id.list_item_driver_status);
            list_item_driver_id = view.findViewById(R.id.list_item_driver_id);
        }
    }

    @NonNull
    @Override
    public AllUserDetAdapter.allUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_user, parent, false);
        return new allUserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserDetAdapter.allUserViewHolder holder, int position) {
        holder.list_item_driver_name.setText(userList.get(position).getUserName());
        holder.list_item_driver_status.setText(userList.get(position).getUserStatus());
        holder.list_item_driver_id.setText(userList.get(position).getUserMobile());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
