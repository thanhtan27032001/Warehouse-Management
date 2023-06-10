package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.ResponseItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ItemApi {
    @GET("/api/v1/product/searchByName")
    Call<ResponseItems> getItemsByName(@Query("q") String itemName);
}
