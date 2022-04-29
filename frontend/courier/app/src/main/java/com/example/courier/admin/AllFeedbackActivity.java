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
import com.example.courier.R;
import com.example.courier.adapters.AllUserDetAdapter;
import com.example.courier.adapters.FeedbackDetAdapter;
import com.example.courier.dto.FeedbackDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFeedbackActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<FeedbackDto> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_feedback);

        recyclerView = findViewById(R.id.rv_customer_feedback);
        progressDialog = new ProgressDialog(AllFeedbackActivity.this);
        mData = new ArrayList<>();
        fnFeedback();
    }

    private void fnFeedback() {
        showProgressBar("Loading feedbacks", "Please wait....");
        AdminApiService adminApiService = ApiCaller.getClient().create(AdminApiService.class);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_WORLD_READABLE);
        Call<ApiResponse<List<FeedbackDto>>> call = adminApiService.getAllFeedbacks(preferences.getString(Constants.TOKEN_STORE, ""));

        call.enqueue(new Callback<ApiResponse<List<FeedbackDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<FeedbackDto>>> call, Response<ApiResponse<List<FeedbackDto>>> response) {
                hideProgressBar();
                if (response.body().isStatus()) {
                    for(FeedbackDto res : response.body().getData()) {
                        mData.add(res);
                    }
                    fnSetAdapter();
                } else{
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<FeedbackDto>>> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fnSetAdapter() {
        FeedbackDetAdapter adapter = new FeedbackDetAdapter(mData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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