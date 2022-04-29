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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.DriverApiService;
import com.example.courier.ApiCaller.OrderApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.MainActivity;
import com.example.courier.Models.ResOrderModel;
import com.example.courier.R;
import com.example.courier.adapters.OrderDetailsAdapter;
import com.example.courier.admin.PendingOrdersActivity;
import com.example.courier.admin.RejectOrderActivity;
import com.example.courier.customer.OrderDetailsActivity;
import com.example.courier.dto.PendingOrderDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverHomeActivity extends AppCompatActivity {
    ImageView img_orders,img_profile;
    RecyclerView rv_orders;
    OrderDetailsAdapter orderDetailsAdapter;
    ArrayList<PendingOrderDetails> mData;
    String order_status;
    private ProgressDialog progressDialog;
    TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        progressDialog = new ProgressDialog(DriverHomeActivity.this);
        img_orders = findViewById(R.id.img_orders);
        img_profile = findViewById(R.id.img_profile);
        rv_orders = findViewById(R.id.rv_order);
        tv_logout = findViewById(R.id.tv_logout);
//        loadOrders();
        fnLoadAllOrders();
        img_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverHomeActivity.this, DriverAcceptedOrdersActivity.class));
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverHomeActivity.this, MainActivity.class));
            }
        });
    }

    private void loadOrders() {
        mData = new ArrayList<>();
        mData.add(new PendingOrderDetails("001","Birthday present","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "P"));
        mData.add(new PendingOrderDetails("002","Gift","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "S"));
        mData.add(new PendingOrderDetails("003","Laptop","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "O"));
        mData.add(new PendingOrderDetails("004","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "A"));
        mData.add(new PendingOrderDetails("005","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "D"));
        mData.add(new PendingOrderDetails("006","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "P"));

        orderDetailsAdapter = new OrderDetailsAdapter(this,mData, DriverHomeActivity.this::onClickListener);
        rv_orders.setAdapter(orderDetailsAdapter);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onClickListener(int position) {
        Intent intent = new Intent(DriverHomeActivity.this, ViewOrderActivity.class);
        intent.putExtra("name",mData.get(position).getReceiver_name());
        intent.putExtra("orderId",mData.get(position).getOrder_id());
        intent.putExtra("description",mData.get(position).getDescription());
        intent.putExtra("pickup_location",mData.get(position).getPickup_location());
        intent.putExtra("receive_location",mData.get(position).getReceive_location());
        intent.putExtra("mobile",mData.get(position).getReceiver_mobile());
        intent.putExtra("status",mData.get(position).getStatus());
        startActivity(intent);
//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Are you reject this order?")
//                .setMessage("This order has been assigned to you. you can reject this order or take this order")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setNegativeButton("Update status", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(DriverHomeActivity.this, ViewOrderActivity.class);
//                        intent.putExtra("name",mData.get(position).getReceiver_name());
//                        intent.putExtra("orderId",mData.get(position).getOrder_id());
//                        intent.putExtra("description",mData.get(position).getDescription());
//                        intent.putExtra("pickup_location",mData.get(position).getPickup_location());
//                        intent.putExtra("receive_location",mData.get(position).getReceive_location());
//                        intent.putExtra("mobile",mData.get(position).getReceiver_mobile());
//                        intent.putExtra("status",mData.get(position).getStatus());
//                        startActivity(intent);
//                    }
//                })
//                .show();
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
                        if(res.getStatus().toString().equals("A")){
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

    private void showProgressBar(String title, String desc) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(desc);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressBar() {
        progressDialog.dismiss();
    }

    public void setAdapter() {

        orderDetailsAdapter = new OrderDetailsAdapter(this,mData, DriverHomeActivity.this::onClickListener);
        rv_orders.setAdapter(orderDetailsAdapter);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
    }
}