package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.LoginInfo;
import com.example.warehousemanagementwkeeper.model.ResponseEmployee;
import com.example.warehousemanagementwkeeper.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthorizationApi {
    @POST("/api/v1/auth/login")
    Call<ResponseLogin> login(@Body LoginInfo loginInfo);

    @GET("/api/v1/auth")
    Call<ResponseEmployee> getEmployeeInfo(@Header("Authorization") String token);
}
