package com.example.courier.ApiCaller;

import com.example.courier.Models.ReqOrderModel;
import com.example.courier.Models.ReqOrderStatusModel;
import com.example.courier.Models.ResOrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DriverApiService {
    @GET("driverdetails/getAllOrder/{userId}")
    Call<ApiResponse<List<ResOrderModel>>> getAllOrderInfo(@Header("Authorization") String bearerToken, @Path("userId") long userId);

    @PUT("driverdetails/updateOrderStatus/")
    Call<ApiResponse<String>> updateOrderStatus(@Header("Authorization") String bearerToken, @Body ReqOrderStatusModel reqOrderStatusModel);

}
