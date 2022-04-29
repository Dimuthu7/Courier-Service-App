package com.example.courier.ApiCaller;

import com.example.courier.Models.ReqRegisterUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("userdetails/register")
    Call<ApiResponse<ReqRegisterUser>> register(@Body ReqRegisterUser user);
}
