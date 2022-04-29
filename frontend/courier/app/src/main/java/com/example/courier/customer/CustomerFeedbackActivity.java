package com.example.courier.customer;

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

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.OrderApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ReqCreateFeedBack;
import com.example.courier.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerFeedbackActivity extends AppCompatActivity {
    TextView tv_desc, tv_price, tv_address_pickup, tv_address_receive, tv_phone;
    EditText et_feedback;
    ConstraintLayout btn_submit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feedback);

        progressDialog = new ProgressDialog(CustomerFeedbackActivity.this);
        tv_desc = findViewById(R.id.tv_desc);
        tv_price = findViewById(R.id.tv_price);
        tv_address_pickup = findViewById(R.id.tv_address_pickup);
        tv_address_receive = findViewById(R.id.tv_address_receive);
        tv_phone = findViewById(R.id.tv_phone);
        et_feedback = findViewById(R.id.et_feedback);
        btn_submit = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String orderId = intent.getStringExtra("id");
        String description = intent.getStringExtra("description");
        String pickup_location = intent.getStringExtra("pickup_location");
        String receive_location = intent.getStringExtra("receive_location");
        String mobile = intent.getStringExtra("mobile");

        tv_address_receive.setText(receive_location);
        tv_phone.setText(mobile);
//        tv_price.setText(tv_price);
        tv_desc.setText(description);
        tv_address_pickup.setText(pickup_location);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);

                createFeedback(new ReqCreateFeedBack(et_feedback.getText().toString(), Long.parseLong(orderId),
                        preferences.getLong(Constants.USER_ID, 1) ));
            }
        });
    }

    private void createFeedback(ReqCreateFeedBack reqCreateFeedBack) {
        showProgressBar("Feedback submitting", "Please wait....");
        OrderApiService orderApiService = ApiCaller.getClient().create(OrderApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<String>> call = orderApiService.createFeedback(preferences.getString(Constants.TOKEN_STORE, ""), reqCreateFeedBack);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    startActivity(new Intent(CustomerFeedbackActivity.this, CustomerHomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Feedback successfully submitted", Toast.LENGTH_LONG).show();
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