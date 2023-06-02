package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.ResponseOrders;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderApi {
    @GET("/api/v1/order/getAllOrder")
    Call<ResponseOrders> getAllOrders();
}
