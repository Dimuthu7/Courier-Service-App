package com.example.courier.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.DriverApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ReqOrderStatusModel;
import com.example.courier.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusActivity extends AppCompatActivity {
    TextView tv_name, tv_address, tv_phone, tv_desc;
    ConstraintLayout btn_get_order,btn_on_the_way,btn_sent;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        progressDialog = new ProgressDialog(OrderStatusActivity.this);
        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        tv_phone = findViewById(R.id.tv_phone);
        tv_desc = findViewById(R.id.tv_desc);
        btn_get_order = findViewById(R.id.btn_get_order);
        btn_on_the_way = findViewById(R.id.btn_on_the_way);
        btn_sent = findViewById(R.id.btn_sent);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String orderId = intent.getStringExtra("orderId");
        String description = intent.getStringExtra("description");
        String pickup_location = intent.getStringExtra("pickup_location");
        String receive_location = intent.getStringExtra("receive_location");
        String mobile = intent.getStringExtra("mobile");
        String status = intent.getStringExtra("status");

        if(status.equals("S")) btn_get_order.setBackground(getDrawable(R.drawable.button_red_bg));
        else if(status.equals("O")) btn_on_the_way.setBackground(getDrawable(R.drawable.button_red_bg));
        else if(status.equals("D")) btn_sent.setBackground(getDrawable(R.drawable.button_red_bg));

        tv_name.setText(name);
        tv_address.setText(receive_location);
        tv_phone.setText(mobile);
        tv_desc.setText(description);

        btn_get_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_get_order.setBackground(getDrawable(R.drawable.button_red_bg));
                btn_on_the_way.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_sent.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                updateOrderStatus(new ReqOrderStatusModel(Long.parseLong(orderId), "S"));
            }
        });

        btn_on_the_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_get_order.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_on_the_way.setBackground(getDrawable(R.drawable.button_red_bg));
                btn_sent.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                updateOrderStatus(new ReqOrderStatusModel(Long.parseLong(orderId), "O"));
            }
        });

        btn_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_get_order.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_on_the_way.setBackground(getDrawable(R.drawable.button_strock_red_bg));
                btn_sent.setBackground(getDrawable(R.drawable.button_red_bg));
                updateOrderStatus(new ReqOrderStatusModel(Long.parseLong(orderId), "D"));
            }
        });
    }

    private void updateOrderStatus(ReqOrderStatusModel reqOrderStatusModel) {
        showProgressBar("Request Processing", "Please wait....");
        DriverApiService driverApiService = ApiCaller.getClient().create(DriverApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<String>> call = driverApiService.updateOrderStatus(preferences.getString(Constants.TOKEN_STORE, ""), reqOrderStatusModel);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    startActivity(new Intent(OrderStatusActivity.this, DriverHomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Order status successfully updated", Toast.LENGTH_LONG).show();
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
}