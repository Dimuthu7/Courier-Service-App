package com.example.courier.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.OrderApiService;
import com.example.courier.ApiCaller.ProfileApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ResOrderModel;
import com.example.courier.Models.ResUserInfoModel;
import com.example.courier.R;
import com.example.courier.adapters.OrderDetailsAdapter;
import com.example.courier.admin.AcceptOrderActivity;
import com.example.courier.admin.PendingOrdersActivity;
import com.example.courier.admin.RejectOrderActivity;
import com.example.courier.dto.PendingOrderDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView tv_order_type;
    RecyclerView rv_order;
    OrderDetailsAdapter orderDetailsAdapter;
    ArrayList<PendingOrderDetails> mData;
    String order_status;
    AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;

    ResOrderModel resOrderModel;
    ArrayList<ResOrderModel> listAllOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        progressDialog = new ProgressDialog(OrderDetailsActivity.this);
        tv_order_type = findViewById(R.id.tv_order_type);
        rv_order = findViewById(R.id.rv_order);
        Intent intent = getIntent();
        order_status = intent.getStringExtra("OrderStatus");
        tv_order_type.setText(order_status + " Orders");
//        loadOrders();
        fnLoadAllOrders();

    }

    private void fnLoadAllOrders() {
        showProgressBar("Loading order details", "Please wait....");
        OrderApiService orderApiService = ApiCaller.getClient().create(OrderApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<List<ResOrderModel>>> call = orderApiService.getAllOrderInfo(preferences.getString(Constants.TOKEN_STORE, ""), preferences.getLong(Constants.USER_ID, 1));

        mData = new ArrayList<>();
        call.enqueue(new Callback<ApiResponse<List<ResOrderModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ResOrderModel>>> call, Response<ApiResponse<List<ResOrderModel>>> response) {
                hideProgressBar();
                if (response.body().isStatus()) {
                    for(ResOrderModel res : response.body().getData()) {
//                        userList.add(new User((user.getUserName()), user.getUserType()));
                        if (order_status.equals("Requested")){
                            if(res.getStatus().toString().equals("P") || res.getStatus().toString().equals("R")){
                                mData.add(new PendingOrderDetails(""+res.getOrderId(),
                                        res.getDescription().toString(),
                                        res.getPickupLocation().toString(),
                                        res.getReceiveLocation().toString(),
                                        res.getReceiverMobile().toString(),
                                        res.getReceiverName().toString(),
                                        res.getStatus().toString()));
                            }
                        } else if (order_status.equals("Pending")){
                            if(res.getStatus().toString().equals("A") || res.getStatus().toString().equals("S") ||
                                    res.getStatus().toString().equals("O")){
                                mData.add(new PendingOrderDetails(""+res.getOrderId(),
                                        res.getDescription().toString(),
                                        res.getPickupLocation().toString(),
                                        res.getReceiveLocation().toString(),
                                        res.getReceiverMobile().toString(),
                                        res.getReceiverName().toString(),
                                        res.getStatus().toString()));
                            }
                        } else if (order_status.equals("Sent")){
                            if(res.getStatus().toString().equals("D")){
                                mData.add(new PendingOrderDetails(""+res.getOrderId(),
                                        res.getDescription().toString(),
                                        res.getPickupLocation().toString(),
                                        res.getReceiveLocation().toString(),
                                        res.getReceiverMobile().toString(),
                                        res.getReceiverName().toString(),
                                        res.getStatus().toString()));
                            }
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

//        call.enqueue(new Callback<ApiResponse<ResOrderModel>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<ResOrderModel>> call, Response<ApiResponse<ResOrderModel>> response) {
//                hideProgressBar();
//                ApiResponse resApi = response.body();
//                ResOrderModel res = (ResOrderModel) resApi.getData();
//
//
//                if(resApi.isStatus()){
////                    listAllOrder = new ArrayList<>();
////                    listAllOrder.add(new ResOrderModel(res.getOrderId(), res.getReceiverName().toString(),
////                            res.getReceiverMobile().toString(),
////                            res.getDescription().toString(), res.getReceiveLocation().toString(),
////                            res.getPickupLocation().toString(), res.getUserName().toString(),
////                            res.getStatus().toString() ));
//
//                    for(ResOrderModel user : response.body().getData()) {
//                        userList.add(new User((user.getUserName()), user.getUserType()));
//                    }
//
//                    if (order_status.equals("Requested")){
////                        for(ResOrderModel resOrderModel: listAllOrder){
////                            if(resOrderModel.getStatus().equals("P")){
////                                mData.add(new PendingOrderDetails(""+resOrderModel.getOrderId(),
////                                        ""+resOrderModel.getDescription(),
////                                        ""+resOrderModel.getPickupLocation(),
////                                        ""+resOrderModel.getReceiveLocation(),
////                                        ""+resOrderModel.getReceiverMobile(),
////                                        ""+resOrderModel.getReceiverName(),
////                                        ""+resOrderModel.getStatus()));
////                            }
////                        }
//
//                        if(res.getStatus().toString().equals("P")){
//                            mData.add(new PendingOrderDetails(""+res.getOrderId(),
//                                    res.getDescription().toString(),
//                                    res.getPickupLocation().toString(),
//                                    res.getReceiveLocation().toString(),
//                                    res.getReceiverMobile().toString(),
//                                    res.getReceiverName().toString(),
//                                    res.getStatus().toString()));
//                        }
//
//                    }else if(order_status.equals("Pending")){
//
//                    }
//
//
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), resApi.getError(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<ResOrderModel>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                hideProgressBar();
//            }
//        });

    }

    private void loadOrders() {
        mData = new ArrayList<>();
        mData.add(new PendingOrderDetails("001","Birthday present","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "P"));
        mData.add(new PendingOrderDetails("002","Gift","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "S"));
        mData.add(new PendingOrderDetails("003","Laptop","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "O"));
        mData.add(new PendingOrderDetails("004","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "A"));
        mData.add(new PendingOrderDetails("005","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "D"));
        mData.add(new PendingOrderDetails("006","Telephone","32/1, galle rd, mt lavinia","Kurunegala","0771234545", "Jhone willey", "P"));

        orderDetailsAdapter = new OrderDetailsAdapter(this,mData, OrderDetailsActivity.this::onClickListener);
        rv_order.setAdapter(orderDetailsAdapter);
        rv_order.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onClickListener(int position) {
        alertDialog = new AlertDialog.Builder(this);

        if(mData.get(position).getStatus().equals("P")){
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you want to remove this pending order?")
                    .setMessage("You can remove this pending order")
                    .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteOrder(mData.get(position).getOrder_id());
                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
        else if(mData.get(position).getStatus().equals("D")){
            Intent intent = new Intent(OrderDetailsActivity.this, CustomerFeedbackActivity.class);
            intent.putExtra("name",mData.get(position).getReceiver_name());
            intent.putExtra("id",mData.get(position).getOrder_id());
            intent.putExtra("description",mData.get(position).getDescription());
            intent.putExtra("pickup_location",mData.get(position).getPickup_location());
            intent.putExtra("receive_location",mData.get(position).getReceive_location());
            intent.putExtra("mobile",mData.get(position).getReceiver_mobile());
            startActivity(intent);
        }
    }

    private void deleteOrder(String order_id) {
        showProgressBar("Request Processing", "Please wait....");
        OrderApiService orderApiService = ApiCaller.getClient().create(OrderApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<String>> call = orderApiService.deleteOrder(preferences.getString(Constants.TOKEN_STORE, ""), Long.parseLong(order_id));

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    startActivity(new Intent(OrderDetailsActivity.this, CustomerHomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Order successfully deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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

        orderDetailsAdapter = new OrderDetailsAdapter(this,mData, OrderDetailsActivity.this::onClickListener);
        rv_order.setAdapter(orderDetailsAdapter);
        rv_order.setLayoutManager(new LinearLayoutManager(this));
    }
}