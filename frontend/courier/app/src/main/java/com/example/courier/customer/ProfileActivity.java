package com.example.courier.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.ProfileApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Login;
import com.example.courier.Models.ReqRegisterUser;
import com.example.courier.Models.ResUserInfoModel;
import com.example.courier.R;
import com.example.courier.RegisterActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    EditText et_name, et_address, et_email, et_mobile;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);
        et_email = findViewById(R.id.et_email);
        et_mobile = findViewById(R.id.et_mobile);
        btn_update = findViewById(R.id.btn_update);
        progressDialog = new ProgressDialog(ProfileActivity.this);

        getUserInfo();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
                updateUserInf(new ResUserInfoModel(preferences.getLong(Constants.USER_ID, 1),
                        et_name.getText().toString(),
                        et_mobile.getText().toString(),
                        et_email.getText().toString(), et_address.getText().toString()));
            }
        });
    }

    private void updateUserInf(ResUserInfoModel userInfoModel) {
        showProgressBar("User Profile Updating", "Please wait....");
        ProfileApiService retrofitAPI = ApiCaller.getClient().create(ProfileApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<String>> call = retrofitAPI.updateInfo(preferences.getString(Constants.TOKEN_STORE, ""), userInfoModel);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    startActivity(new Intent(ProfileActivity.this, CustomerHomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Profile Updating Successfully", Toast.LENGTH_LONG).show();
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

    private void getUserInfo() {
        showProgressBar("User Profile", "Please wait....");
        ProfileApiService retrofitAPI = ApiCaller.getClient().create(ProfileApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<ResUserInfoModel>> call = retrofitAPI.getUserInfo(preferences.getString(Constants.TOKEN_STORE, ""), preferences.getLong(Constants.USER_ID, 1));

        call.enqueue(new Callback<ApiResponse<ResUserInfoModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<ResUserInfoModel>> call, Response<ApiResponse<ResUserInfoModel>> response) {
                hideProgressBar();
                ApiResponse resApi = response.body();
                ResUserInfoModel res = (ResUserInfoModel) resApi.getData();

                if(resApi.isStatus()){
                    et_name.setText(res.getUserName().toString());
                    et_address.setText(res.getUserAddress().toString());
                    et_email.setText(res.getUserEmail().toString());
                    et_mobile.setText(res.getUserMobile().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), resApi.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ResUserInfoModel>> call, Throwable t) {
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
}