package com.example.courier.ApiCaller;

import com.example.courier.Models.ReqCreateFeedBack;
import com.example.courier.Models.ReqOrderModel;
import com.example.courier.Models.ResOrderModel;
import com.example.courier.Models.ResUserInfoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApiService {
    @POST("customerdetails/createOrder")
    Call<ApiResponse<String>> createOrder(@Header("Authorization") String bearerToken, @Body ReqOrderModel reqOrderModel);

    @GET("customerdetails/getAllOrderInfo/{userId}")
    Call<ApiResponse<List<ResOrderModel>>> getAllOrderInfo(@Header("Authorization") String bearerToken, @Path("userId") long userId);

    @POST("customerdetails/createFeedback")
    Call<ApiResponse<String>> createFeedback(@Header("Authorization") String bearerToken, @Body ReqCreateFeedBack reqCreateFeedBack);

    @DELETE("customerdetails/deleteOrder/{orderId}")
    Call<ApiResponse<String>> deleteOrder(@Header("Authorization") String bearerToken, @Path("orderId") long userId);

}
