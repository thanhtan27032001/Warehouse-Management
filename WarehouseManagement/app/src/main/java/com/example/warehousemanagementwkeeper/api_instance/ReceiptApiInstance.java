package com.example.warehousemanagementwkeeper.api_instance;

import com.example.warehousemanagementwkeeper.api.ApiUrl;
import com.example.warehousemanagementwkeeper.api.ReceiptApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceiptApiInstance {
    private static ReceiptApi instance;
    public static ReceiptApi getInstance(){
        if (instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(ApiUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ReceiptApi.class);
        }
        return instance;
    }
}
