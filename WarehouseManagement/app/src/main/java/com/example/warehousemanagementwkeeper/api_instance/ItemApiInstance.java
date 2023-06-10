package com.example.warehousemanagementwkeeper.api_instance;

import com.example.warehousemanagementwkeeper.api.ApiUrl;
import com.example.warehousemanagementwkeeper.api.DeliveryNoteApi;
import com.example.warehousemanagementwkeeper.api.ItemApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemApiInstance {
    private static ItemApi instance;
    public static ItemApi getInstance(){
        if (instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(ApiUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ItemApi.class);
        }
        return instance;
    }
}
