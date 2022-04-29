package com.example.courier.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courier.ApiCaller.AdminApiService;
import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ReqAppOrderModel;
import com.example.courier.Models.ResAdminAllUserInfoModel;
import com.example.courier.Models.ResUserInfoModel;
import com.example.courier.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RejectOrderActivity extends AppCompatActivity {
    TextView textView_name, textView_address, textView_phone, textView_desc;
    EditText editText_reason;
    ConstraintLayout btn_reject_order;
    private ProgressDialog progressDialog;
    List<ResUserInfoModel> userList;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_order);

        textView_name = findViewById(R.id.textView_name);
        textView_address = findViewById(R.id.textView_address);
        textView_phone = findViewById(R.id.textView_phone);
        textView_desc = findViewById(R.id.textView_desc);
        editText_reason = findViewById(R.id.editText_reason);
        btn_reject_order = findViewById(R.id.btn_reject_order);
        progressDialog = new ProgressDialog(RejectOrderActivity.this);

        Intent intent = getIntent();
        String receiverName = intent.getStringExtra("receiver_name");
        String description = intent.getStringExtra("description");
        String pickup_location = intent.getStringExtra("pickup_location");
        String receive_location = intent.getStringExtra("receive_location");
        String mobile = intent.getStringExtra("mobile");
        String userName = intent.getStringExtra("user_name");
        String orderId = intent.getStringExtra("id");

        textView_name.setText(receiverName);
        textView_address.setText(receive_location);
        textView_phone.setText(mobile);
        textView_desc.setText(description);

        getUserId(userName);

        btn_reject_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RejectOrder(new ReqAppOrderModel("R", 0, editText_reason.getText().toString(),
                        Long.parseLong(orderId), userId, 0));
            }
        });
    }

    private void RejectOrder(ReqAppOrderModel reqAppOrderModel) {
        showProgressBar("Order rejecting", "Please wait....");
        AdminApiService adminApiService = ApiCaller.getClient().create(AdminApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<String>> call = adminApiService.ApproveOrder(preferences.getString(Constants.TOKEN_STORE, ""), reqAppOrderModel);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    startActivity(new Intent(RejectOrderActivity.this, AdminHomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Order rejecting Successfully", Toast.LENGTH_LONG).show();
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

    private void getUserId(String userName) {
        showProgressBar("Loading drivers", "Please wait....");
        AdminApiService adminApiService = ApiCaller.getClient().create(AdminApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<List<ResAdminAllUserInfoModel>>> call = adminApiService.getAllUsers(preferences.getString(Constants.TOKEN_STORE, ""));

        call.enqueue(new Callback<ApiResponse<List<ResAdminAllUserInfoModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ResAdminAllUserInfoModel>>> call, Response<ApiResponse<List<ResAdminAllUserInfoModel>>> response) {
                hideProgressBar();
                if (response.body().isStatus()) {
                    for(ResAdminAllUserInfoModel res : response.body().getData()) {
                        if(res.getUserName().equals(userName)){
                            userId = res.getUserId();
                        }
                    }
                } else{
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ResAdminAllUserInfoModel>>> call, Throwable t) {
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
}