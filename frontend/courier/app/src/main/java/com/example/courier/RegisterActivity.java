package com.example.courier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.ApiCaller.UserApiService;
import com.example.courier.Models.ReqRegisterUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView tv_login;
    EditText edt_user_name, edt_password, edt_address, edt_email, edt_mobile;
    ConstraintLayout btn_register;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_login = findViewById(R.id.tv_login);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_password);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);
        btn_register = findViewById(R.id.btn_register);

        progressDialog = new ProgressDialog(RegisterActivity.this);

        Intent intent = getIntent();
        String type = intent.getStringExtra("Type");

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,Login.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register(new ReqRegisterUser(edt_user_name.getText().toString(), edt_password.getText().toString(), edt_mobile.getText().toString(),
                        edt_email.getText().toString(), edt_address.getText().toString(), type));
            }
        });
    }

    private void Register(ReqRegisterUser user) {
        showProgressBar("Register User", "Please wait to register details");
        UserApiService retrofitAPI = ApiCaller.getClient().create(UserApiService.class);
        Call<ApiResponse<ReqRegisterUser>> call = retrofitAPI.register(user);

        call.enqueue(new Callback<ApiResponse<ReqRegisterUser>>() {
            @Override
            public void onResponse(Call<ApiResponse<ReqRegisterUser>> call, Response<ApiResponse<ReqRegisterUser>> response) {
                hideProgressBar();
                if(response.isSuccessful()) {
                    ReqRegisterUser user = response.body().getData();
                    startActivity(new Intent(RegisterActivity.this,Login.class));
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ReqRegisterUser>> call, Throwable t) {
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