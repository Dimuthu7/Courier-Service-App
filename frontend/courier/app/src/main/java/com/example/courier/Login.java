package com.example.courier;

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
import com.example.courier.ApiCaller.SignInApiService;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ReqLoginModel;
import com.example.courier.Models.ResLogin;
import com.example.courier.admin.AdminHomeActivity;
import com.example.courier.customer.CustomerHomeActivity;
import com.example.courier.driver.DriverHomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextView tv_register;
    EditText edt_user_name,edt_password;
    ConstraintLayout btn_login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_register = findViewById(R.id.tv_register);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);

        progressDialog = new ProgressDialog(Login.this);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String type = intent.getStringExtra("Type");

                Intent intent2 = new Intent(Login.this, RegisterActivity.class);
                intent2.putExtra("Type",type);
                startActivity(intent2);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name,password;
                user_name = edt_user_name.getText().toString().trim();
                password = edt_password.getText().toString().trim();

                SignIn(user_name, password);

//                Intent intent = getIntent();
//                String customer_type = intent.getStringExtra("Type");
//
//                if(user_name.equals("a")){
//                    startActivity(new Intent(Login.this, AdminHomeActivity.class));
//                }
//                else{
//                    if (customer_type.equals("Customer")){
//                        startActivity(new Intent(Login.this, CustomerHomeActivity.class));
//                    } else {
//                        startActivity(new Intent(Login.this, DriverHomeActivity.class));
//                    }
//                }

            }
        });
    }

    private void SignIn(String userName, String userPassword) {
        showProgressBar("Sign In", "Please wait to verify login details");
        SignInApiService retrofitAPI = ApiCaller.getClient().create(SignInApiService.class);
        Call<ApiResponse<ResLogin>> call = retrofitAPI.signIn(new ReqLoginModel(userName, userPassword));

        call.enqueue(new Callback<ApiResponse<ResLogin>>() {
            @Override
            public void onResponse(Call<ApiResponse<ResLogin>> call, Response<ApiResponse<ResLogin>> response) {
                hideProgressBar();
                ApiResponse resApi = response.body();
                ResLogin res = (ResLogin) resApi.getData();

                if(resApi.isStatus()){

                    SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(Constants.TOKEN_STORE, "Bearer " + res.getToken().toString());
                    edit.putLong(Constants.USER_ID, res.getUserId());
                    edit.putString(Constants.USER_NAME, res.getUserName().toString());
                    edit.putString(Constants.USER_TYPE, res.getUserType().toString());
                    edit.commit();

                    if(res.getUserType().equals("C"))
                        startActivity(new Intent(Login.this, CustomerHomeActivity.class));
                    else if(res.getUserType().equals("A"))
                        startActivity(new Intent(Login.this, AdminHomeActivity.class));
                    else if(res.getUserType().equals("D"))
                        startActivity(new Intent(Login.this, DriverHomeActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), resApi.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ResLogin>> call, Throwable t) {
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