package com.example.courier.ApiCaller;

import com.example.courier.Models.ReqLoginModel;
import com.example.courier.Models.ResLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInApiService {
    @POST("auth/")
    Call<ApiResponse<ResLogin>> signIn(@Body ReqLoginModel login);
}
