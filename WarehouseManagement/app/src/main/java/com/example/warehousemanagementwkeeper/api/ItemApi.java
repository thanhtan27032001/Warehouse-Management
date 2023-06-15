package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.PutItemToShelfInfo;
import com.example.warehousemanagementwkeeper.model.ResponseItemLocations;
import com.example.warehousemanagementwkeeper.model.ResponseItems;
import com.example.warehousemanagementwkeeper.model.ResponseItemsNotInShelf;
import com.example.warehousemanagementwkeeper.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemApi {
    @GET("/api/v1/product/searchByName1")
    Call<ResponseItems> getItemsByName(@Query("q") String itemName);

    // get items start with <itemId>
    @GET("/api/v1/product/getProductById/{itemId}")
    Call<ResponseItems> getItemsById(@Path("itemId") String itemId);

    @GET("/api/v1/product/searchById/{itemId}")
    Call<ResponseItemLocations> getItemLocations(@Path("itemId") String itemId);

    @GET("/api/v1/product/getProductNotInBox")
    Call<ResponseItemsNotInShelf> getItemsNotInShelf();

    @POST("/api/v1/product/create")
    Call<ResponseObject> putItemToShelf(@Body PutItemToShelfInfo info);
}
