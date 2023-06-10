package com.example.warehousemanagementwkeeper.api_instance;

import com.example.warehousemanagementwkeeper.api.ApiUrl;
import com.example.warehousemanagementwkeeper.api.DeliveryNoteApi;
import com.example.warehousemanagementwkeeper.api.ReceiptApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeliveryNoteApiInstance {
    private static DeliveryNoteApi instance;
    public static DeliveryNoteApi getInstance(){
        if (instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(ApiUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DeliveryNoteApi.class);
        }
        return instance;
    }
}
