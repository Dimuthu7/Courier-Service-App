package com.example.courier.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courier.ApiCaller.AdminApiService;
import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.ProfileApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ReqAppOrderModel;
import com.example.courier.Models.ResAdminAllUserInfoModel;
import com.example.courier.Models.ResOrderModel;
import com.example.courier.Models.ResUserInfoModel;
import com.example.courier.R;
import com.example.courier.customer.CustomerHomeActivity;
import com.example.courier.customer.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptOrderActivity extends AppCompatActivity {
    TextView textView_name, textView_address, textView_phone, textView_desc;
    Spinner spinner;
    EditText editText_price;
    private ProgressDialog progressDialog;
    List<String> driverList;
    ConstraintLayout btn_accept_order;
    List<ResUserInfoModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order);

        textView_name = findViewById(R.id.textView_name);
        textView_address = findViewById(R.id.textView_address);
        textView_phone = findViewById(R.id.textView_phone);
        textView_desc = findViewById(R.id.textView_desc);
        spinner = findViewById(R.id.spinner);
        btn_accept_order = findViewById(R.id.btn_accept_order);
        editText_price = findViewById(R.id.editText_price);
        progressDialog = new ProgressDialog(AcceptOrderActivity.this);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");
        String receiverName = intent.getStringExtra("receiver_name");
        String orderId = intent.getStringExtra("id");
        String description = intent.getStringExtra("description");
        String pickup_location = intent.getStringExtra("pickup_location");
        String receive_location = intent.getStringExtra("receive_location");
        String mobile = intent.getStringExtra("mobile");

        textView_name.setText(receiverName);
        textView_address.setText(receive_location);
        textView_phone.setText(mobile);
        textView_desc.setText(description);

        getAllUsers();

        btn_accept_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String driverName =  String.valueOf(spinner.getSelectedItem());
                long driverId = 0;
                long userId = 0;

                for(ResUserInfoModel resUserInfoModel: userList){
                    if(resUserInfoModel.getUserName().equals(driverName))
                        driverId = resUserInfoModel.getUserId();
                    if(resUserInfoModel.getUserName().equals(userName.toString()))
                        userId = resUserInfoModel.getUserId();
                }
                double price = Double.parseDouble(editText_price.getText().toString());

                ApproveOrder(new ReqAppOrderModel("A", price, "", Long.parseLong(orderId),
                        userId, driverId));
            }
        });
    }

    private void ApproveOrder(ReqAppOrderModel reqAppOrderModel) {
        showProgressBar("Order accepting", "Please wait....");
        AdminApiService adminApiService = ApiCaller.getClient().create(AdminApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<String>> call = adminApiService.ApproveOrder(preferences.getString(Constants.TOKEN_STORE, ""), reqAppOrderModel);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    startActivity(new Intent(AcceptOrderActivity.this, AdminHomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Order accepting Successfully", Toast.LENGTH_LONG).show();
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

    private void getAllUsers() {
        showProgressBar("Loading drivers", "Please wait....");
        AdminApiService adminApiService = ApiCaller.getClient().create(AdminApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<List<ResAdminAllUserInfoModel>>> call = adminApiService.getAllUsers(preferences.getString(Constants.TOKEN_STORE, ""));

        driverList = new ArrayList<String>();
        userList = new ArrayList<ResUserInfoModel>();
        call.enqueue(new Callback<ApiResponse<List<ResAdminAllUserInfoModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ResAdminAllUserInfoModel>>> call, Response<ApiResponse<List<ResAdminAllUserInfoModel>>> response) {
                hideProgressBar();
                if (response.body().isStatus()) {
                    for(ResAdminAllUserInfoModel res : response.body().getData()) {
                        if(res.getUserType().equals("D") && res.getUserStatus().equals("A")){
                            driverList.add(res.getUserName());
                        }
                        userList.add(new ResUserInfoModel(res.getUserId(), res.getUserName().toString(),
                                res.getUserMobile().toString(), res.getUserEmail().toString(),
                                res.getUserAddress().toString()));
                    }
                    fnSetSpinner();
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

    private void fnSetSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, driverList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
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