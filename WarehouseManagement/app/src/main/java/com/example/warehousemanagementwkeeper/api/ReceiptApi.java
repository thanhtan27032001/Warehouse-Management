package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReceiptApi {
    @POST("/api/v1/order/createReceipt/{orderId}")
    Call<ResponseObject> createReceipt(@Header("Authorization") String token, @Path("orderId") int orderId, @Body Receipt receipt);
}
