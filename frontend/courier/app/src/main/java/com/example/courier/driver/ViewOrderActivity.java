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
import com.example.courier.ApiCaller.OrderApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ReqOrderStatusModel;
import com.example.courier.R;
import com.example.courier.customer.CustomerHomeActivity;
import com.example.courier.customer.SendOrderActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderActivity extends AppCompatActivity {
    TextView edt_rec_name, edt_rec_address, edt_rec_phone, edt_desc;
    ConstraintLayout btn_picked;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        progressDialog = new ProgressDialog(ViewOrderActivity.this);
        edt_rec_name = findViewById(R.id.edt_rec_name);
        edt_rec_address = findViewById(R.id.edt_rec_address);
        edt_rec_phone = findViewById(R.id.edt_rec_phone);
        edt_desc = findViewById(R.id.edt_desc);
        btn_picked = findViewById(R.id.btn_picked);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String orderId = intent.getStringExtra("orderId");
        String description = intent.getStringExtra("description");
        String pickup_location = intent.getStringExtra("pickup_location");
        String receive_location = intent.getStringExtra("receive_location");
        String mobile = intent.getStringExtra("mobile");

        edt_rec_name.setText(name);
        edt_rec_address.setText(receive_location);
        edt_rec_phone.setText(mobile);
        edt_desc.setText(description);

        btn_picked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOrderStatus(new ReqOrderStatusModel(Long.parseLong(orderId), "S"));
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
                    startActivity(new Intent(ViewOrderActivity.this, DriverHomeActivity.class));
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