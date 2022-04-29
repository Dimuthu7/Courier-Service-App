package com.example.courier.ApiCaller;

import com.example.courier.Models.ReqAppOrderModel;
import com.example.courier.Models.ReqRegisterUser;
import com.example.courier.Models.ResAdminAllUserInfoModel;
import com.example.courier.Models.ResOrderModel;
import com.example.courier.Models.ResUserInfoModel;
import com.example.courier.dto.FeedbackDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AdminApiService {
    @GET("admindetails/getAllUsers/")
    Call<ApiResponse<List<ResAdminAllUserInfoModel>>> getAllUsers(@Header("Authorization") String bearerToken);

    @GET("admindetails/getAllOrders/")
    Call<ApiResponse<List<ResOrderModel>>> getAllOrders(@Header("Authorization") String bearerToken);

    @POST("admindetails/approveOrder/")
    Call<ApiResponse<String>> ApproveOrder(@Header("Authorization") String bearerToken, @Body ReqAppOrderModel appOrderModel);

    @GET("admindetails/getAllFeedbacks/")
    Call<ApiResponse<List<FeedbackDto>>> getAllFeedbacks(@Header("Authorization") String bearerToken);
}
