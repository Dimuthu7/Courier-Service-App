package com.example.courier.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.courier.ApiCaller.AdminApiService;
import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.ProfileApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Login;
import com.example.courier.MainActivity;
import com.example.courier.Models.ResAdminAllUserInfoModel;
import com.example.courier.Models.ResOrderModel;
import com.example.courier.Models.ResUserInfoModel;
import com.example.courier.R;
import com.example.courier.adapters.PendingOrderDetAdapter;
import com.example.courier.customer.ProfileActivity;
import com.example.courier.dto.PendingOrderDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrdersActivity extends AppCompatActivity {
    private ArrayList<PendingOrderDetails> orderDetailsList;
    private RecyclerView recyclerView;
    private PendingOrderDetAdapter.RecyclerViewClickListener listener;
    AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);
        recyclerView = findViewById(R.id.pending_order);

        orderDetailsList = new ArrayList<>();
        progressDialog = new ProgressDialog(PendingOrdersActivity.this);

//        setOrderDetails();
        getAllOrders();
    }

    private void getAllOrders() {
        showProgressBar("Loading pending orders", "Please wait....");
        AdminApiService adminApiService = ApiCaller.getClient().create(AdminApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<List<ResOrderModel>>> call = adminApiService.getAllOrders(preferences.getString(Constants.TOKEN_STORE, ""));

        call.enqueue(new Callback<ApiResponse<List<ResOrderModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ResOrderModel>>> call, Response<ApiResponse<List<ResOrderModel>>> response) {
                hideProgressBar();
                if (response.body().isStatus()) {
                    for(ResOrderModel res : response.body().getData()) {
                        if(res.getStatus().toString().equals("P")){
                            orderDetailsList.add(new PendingOrderDetails(""+res.getOrderId(),
                                    res.getDescription().toString(), res.getPickupLocation().toString(),
                                    res.getReceiveLocation().toString(), res.getReceiverMobile().toString(),
                                    res.getReceiverName().toString(), res.getStatus().toString(),
                                    res.getUserName().toString()
                            ));
                        }
                    }
                    setAdapter();
                } else{
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ResOrderModel>>> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setAdapter() {
        setOnClickListner();
        PendingOrderDetAdapter adapter = new PendingOrderDetAdapter(orderDetailsList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void setOnClickListner() {
        alertDialog = new AlertDialog.Builder(this);

        listener = new PendingOrderDetAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you accept this order?")
                        .setMessage("You can accept this order or reject this order")
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(PendingOrdersActivity.this, AcceptOrderActivity.class);
                                intent.putExtra("receiver_name",orderDetailsList.get(position).getReceiver_name());
                                intent.putExtra("id",orderDetailsList.get(position).getOrder_id());
                                intent.putExtra("description",orderDetailsList.get(position).getDescription());
                                intent.putExtra("pickup_location",orderDetailsList.get(position).getPickup_location());
                                intent.putExtra("receive_location",orderDetailsList.get(position).getReceive_location());
                                intent.putExtra("mobile",orderDetailsList.get(position).getReceiver_mobile());
                                intent.putExtra("user_name",orderDetailsList.get(position).getUserName());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(PendingOrdersActivity.this, RejectOrderActivity.class);
                                intent.putExtra("receiver_name",orderDetailsList.get(position).getReceiver_name());
                                intent.putExtra("id",orderDetailsList.get(position).getOrder_id());
                                intent.putExtra("description",orderDetailsList.get(position).getDescription());
                                intent.putExtra("pickup_location",orderDetailsList.get(position).getPickup_location());
                                intent.putExtra("receive_location",orderDetailsList.get(position).getReceive_location());
                                intent.putExtra("mobile",orderDetailsList.get(position).getReceiver_mobile());
                                intent.putExtra("user_name",orderDetailsList.get(position).getUserName());
                                startActivity(intent);
                            }
                        })
                        .show();

//                Toast.makeText(getApplicationContext(), orderDetailsList.get(position).getReceiver_name(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setOrderDetails() {
        orderDetailsList.add(new PendingOrderDetails("001", "Item 01", "Kurunegala",
                "Malabe", "766944088", "Dimuthu", "P"));
        orderDetailsList.add(new PendingOrderDetails("002", "Item 02", "Ambalangoda",
                "Gampaha", "776944088", "Thamal", "P"));
    }

    private void showProgressBar(String title, String desc) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(desc);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressBar() {
        progressDialog.dismiss();
    }
}