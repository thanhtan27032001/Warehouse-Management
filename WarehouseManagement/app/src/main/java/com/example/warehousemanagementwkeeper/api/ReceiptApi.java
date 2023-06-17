package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.model.ImportDetailsUpsertInfo;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseImportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.model.ResponseReceipts;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReceiptApi {
    @POST("/api/v1/order/createReceipt/{orderId}")
    Call<ResponseObject> createReceipt(@Header("Authorization") String token, @Path("orderId") int orderId, @Body Receipt receipt);

    @GET("/api/v1/order/getReceipt")
    Call<ResponseReceipts> getAllReceipt();

    @GET("/api/v1/order/getDetailReceipt/{receiptId}")
    Call<ResponseImportDetails> getReceiptImportDetails(@Path("receiptId") int receiptId);

    @POST("/api/v1/order/createDetailReceipt/{receiptId}")
    Call<ResponseObject> upsertReceiptImportDetail(@Header("Authorization") String token, @Path("receiptId") int receiptId, @Body ImportDetailsUpsertInfo importDetails);

    @PUT("/api/v1/order/updateStatus/{receiptId}")
    Call<ResponseObject> updateReceiptStatus(@Header("Authorization") String token, @Path("receiptId") int receiptId, @Body Receipt receipt);

    @DELETE("/api/v1/order/delete/{receiptId}")
    Call<ResponseObject> deleteReceipt(@Header("Authorization") String token, @Path("receiptId") int receiptId);
}
