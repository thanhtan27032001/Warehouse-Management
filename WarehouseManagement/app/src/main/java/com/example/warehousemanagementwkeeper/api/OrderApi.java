package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.ResponseOrderDetails;
import com.example.warehousemanagementwkeeper.model.ResponseOrders;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderApi {
    @GET("/api/v1/order/getAllOrder")
    Call<ResponseOrders> getAllOrders();

    @GET("/api/v1/order/getDetailOrder/{orderId}")
    Call<ResponseOrderDetails> getOrderDetails(@Path("orderId") int orderId);
}
