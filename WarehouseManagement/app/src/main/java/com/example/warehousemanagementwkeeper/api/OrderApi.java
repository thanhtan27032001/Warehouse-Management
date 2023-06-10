package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.ResponseOrderExportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseOrderImportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseOrdersExport;
import com.example.warehousemanagementwkeeper.model.ResponseOrdersImport;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderApi {
    @GET("/api/v1/order/getAllOrder")
    Call<ResponseOrdersImport> getAllOrdersImport();

    @GET("/api/v1/order/getDetailOrder/{orderId}")
    Call<ResponseOrderImportDetails> getOrderImportDetails(@Path("orderId") int orderId);

    @GET("/api/v1/sell/getAllOrder")
    Call<ResponseOrdersExport> getAllOrdersExport();

    @GET("/api/v1/sell/getDetailOrder/{orderId}")
    Call<ResponseOrderExportDetails> getOrderExportDetails(@Path("orderId") int orderId);
}
