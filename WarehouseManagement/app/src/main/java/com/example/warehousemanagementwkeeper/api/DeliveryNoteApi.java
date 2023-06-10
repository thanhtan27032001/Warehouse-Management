package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeliveryNoteApi {
    @POST("/api/v1/sell/createDeliveryBill/{orderId}")
    Call<ResponseObject> createDeliveryNote(@Header("Authorization") String token, @Path("orderId") int orderId, @Body DeliveryNote deliveryNote);
}
