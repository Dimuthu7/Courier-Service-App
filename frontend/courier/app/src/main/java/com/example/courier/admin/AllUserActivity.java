package com.example.courier.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.courier.ApiCaller.AdminApiService;
import com.example.courier.ApiCaller.ApiCaller;
import com.example.courier.ApiCaller.ApiResponse;
import com.example.courier.Constants.Constants;
import com.example.courier.Models.ResAdminAllUserInfoModel;
import com.example.courier.Models.ResUserInfoModel;
import com.example.courier.R;
import com.example.courier.adapters.AllUserDetAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllUserActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<ResAdminAllUserInfoModel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        recyclerView = findViewById(R.id.registered_users);
        progressDialog = new ProgressDialog(AllUserActivity.this);
        mData = new ArrayList<>();
        fnLoadUsers();

    }

    private void fnSetAdapter() {
        AllUserDetAdapter adapter = new AllUserDetAdapter(mData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void fnLoadUsers() {

//        mData.add(new ResAdminAllUserInfoModel(1,"d","0766944088",
//                "d@gmail.com","kuruenagala", "A", "D"));

        showProgressBar("Loading users", "Please wait....");
        AdminApiService adminApiService = ApiCaller.getClient().create(AdminApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<List<ResAdminAllUserInfoModel>>> call = adminApiService.getAllUsers(preferences.getString(Constants.TOKEN_STORE, ""));

        call.enqueue(new Callback<ApiResponse<List<ResAdminAllUserInfoModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ResAdminAllUserInfoModel>>> call, Response<ApiResponse<List<ResAdminAllUserInfoModel>>> response) {
                hideProgressBar();
                if (response.body().isStatus()) {
                    for(ResAdminAllUserInfoModel res : response.body().getData()) {
                        mData.add(res);
                    }
                    fnSetAdapter();
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