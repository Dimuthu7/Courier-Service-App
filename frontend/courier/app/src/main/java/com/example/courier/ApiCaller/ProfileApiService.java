package com.example.courier.ApiCaller;

import com.example.courier.Models.ReqRegisterUser;
import com.example.courier.Models.ResUserInfoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileApiService {
    @GET("userdetails/getUserInfo/{userId}")
    Call<ApiResponse<ResUserInfoModel>> getUserInfo(@Header("Authorization") String bearerToken, @Path("userId") long userId);

    @PUT("userdetails/updateInfo")
    Call<ApiResponse<String>> updateInfo(@Header("Authorization") String bearerToken, @Body ResUserInfoModel userDetails);
}
