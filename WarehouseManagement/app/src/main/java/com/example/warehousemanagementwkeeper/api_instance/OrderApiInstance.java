package com.example.warehousemanagementwkeeper.api_instance;

import com.example.warehousemanagementwkeeper.api.ApiUrl;
import com.example.warehousemanagementwkeeper.api.OrderApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderApiInstance {
    private static OrderApi instance;
    public static OrderApi getInstance(){
        if (instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(ApiUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OrderApi.class);
        }
        return instance;
    }
}
