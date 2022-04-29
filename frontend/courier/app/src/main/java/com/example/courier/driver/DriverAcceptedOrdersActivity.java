package com.example.courier.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.DriverApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ResOrderModel;
import com.example.courier.R;
import com.example.courier.adapters.OrderDetailsAdapter;
import com.example.courier.dto.PendingOrderDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAcceptedOrdersActivity extends AppCompatActivity {
    RecyclerView rv_accepted_orders;
    OrderDetailsAdapter orderDetailsAdapter;
    ArrayList<PendingOrderDetails> mData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accepted_orders);

        progressDialog = new ProgressDialog(DriverAcceptedOrdersActivity.this);
        rv_accepted_orders = findViewById(R.id.rv_accepted_orders);
//        loadOrders();
        fnLoadAllOrders();
    }

    private void loadOrders() {
        mData = new ArrayList<>();
        mData.add(new PendingOrderDetails("001","Birthday present","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "P"));
        mData.add(new PendingOrderDetails("002","Gift","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "S"));
        mData.add(new PendingOrderDetails("003","Laptop","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "O"));
        mData.add(new PendingOrderDetails("004","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "A"));
        mData.add(new PendingOrderDetails("005","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "D"));
        mData.add(new PendingOrderDetails("006","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "P"));

        orderDetailsAdapter = new OrderDetailsAdapter(this,mData, DriverAcceptedOrdersActivity.this::onClickListener);
        rv_accepted_orders.setAdapter(orderDetailsAdapter);
        rv_accepted_orders.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onClickListener(int position) {
        Intent intent = new Intent(DriverAcceptedOrdersActivity.this, OrderStatusActivity.class);
        intent.putExtra("name",mData.get(position).getReceiver_name());
        intent.putExtra("orderId",mData.get(position).getOrder_id());
        intent.putExtra("description",mData.get(position).getDescription());
        intent.putExtra("pickup_location",mData.get(position).getPickup_location());
        intent.putExtra("receive_location",mData.get(position).getReceive_location());
        intent.putExtra("mobile",mData.get(position).getReceiver_mobile());
        intent.putExtra("status",mData.get(position).getStatus());
        startActivity(intent);
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

    private void fnLoadAllOrders() {
        showProgressBar("Loading order details", "Please wait....");
        DriverApiService driverApiService = ApiCaller.getClient().create(DriverApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<List<ResOrderModel>>> call = driverApiService.getAllOrderInfo(preferences.getString(Constants.TOKEN_STORE, ""), preferences.getLong(Constants.USER_ID, 1));

        mData = new ArrayList<>();
        call.enqueue(new Callback<ApiResponse<List<ResOrderModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ResOrderModel>>> call, Response<ApiResponse<List<ResOrderModel>>> response) {
                hideProgressBar();
                if (response.body().isStatus()) {
                    for(ResOrderModel res : response.body().getData()) {
//                        userList.add(new User((user.getUserName()), user.getUserType()));
                        if(res.getStatus().toString().equals("S") || res.getStatus().toString().equals("O")){
                            mData.add(new PendingOrderDetails(""+res.getOrderId(),
                                    res.getDescription().toString(),
                                    res.getPickupLocation().toString(),
                                    res.getReceiveLocation().toString(),
                                    res.getReceiverMobile().toString(),
                                    res.getReceiverName().toString(),
                                    res.getStatus().toString()));
                        }
                    }
                    setAdapter();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ResOrderModel>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgressBar();
            }
        });

    }

    public void setAdapter() {

        orderDetailsAdapter = new OrderDetailsAdapter(this,mData, DriverAcceptedOrdersActivity.this::onClickListener);
        rv_accepted_orders.setAdapter(orderDetailsAdapter);
        rv_accepted_orders.setLayoutManager(new LinearLayoutManager(this));
    }

}