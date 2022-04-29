package com.example.courier.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.OrderApiService;
import com.example.courier.ApiCaller.ProfileApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ReqOrderModel;
import com.example.courier.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOrderActivity extends AppCompatActivity {
    EditText edt_rec_name, edt_rec_address, edt_pick_address, edt_desc, edt_pick_phone;
    ConstraintLayout btn_send;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        edt_rec_name = findViewById(R.id.edt_rec_name);
        edt_rec_address = findViewById(R.id.edt_rec_address);
        edt_pick_address = findViewById(R.id.edt_pick_address);
        edt_desc = findViewById(R.id.edt_desc);
        edt_pick_phone = findViewById(R.id.edt_pick_phone);
        btn_send = findViewById(R.id.btn_send);

        progressDialog = new ProgressDialog(SendOrderActivity.this);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);

                createOrder(new ReqOrderModel(edt_rec_name.getText().toString().trim(),
                        edt_pick_phone.getText().toString().trim(), edt_desc.getText().toString().trim(),
                        edt_rec_address.getText().toString().trim(), edt_pick_address.getText().toString().trim(),
                        preferences.getLong(Constants.USER_ID, 1) ));
            }
        });
    }

    private void createOrder(ReqOrderModel reqOrderModel) {
        showProgressBar("Request Processing", "Please wait....");
        OrderApiService orderApiService = ApiCaller.getClient().create(OrderApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<String>> call = orderApiService.createOrder(preferences.getString(Constants.TOKEN_STORE, ""), reqOrderModel);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    startActivity(new Intent(SendOrderActivity.this, CustomerHomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Order successfully created", Toast.LENGTH_LONG).show();
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