package com.example.warehousemanagementwkeeper.api;

import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.ExportDetailsUpsertInfo;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseDeliveryNotes;
import com.example.warehousemanagementwkeeper.model.ResponseExportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeliveryNoteApi {
    @POST("/api/v1/sell/createDeliveryBill/{orderId}")
    Call<ResponseObject> createDeliveryNote(@Header("Authorization") String token, @Path("orderId") int orderId, @Body DeliveryNote deliveryNote);

    @GET("/api/v1/sell/getDeliveryBill")
    Call<ResponseDeliveryNotes> getAllDeliveryNotes();

    @PUT("/api/v1/sell/updateStatus/{deliveryNoteId}")
    Call<ResponseObject> updateDeliveryNoteStatus(
            @Header("Authorization") String token,
            @Path("deliveryNoteId") int deliveryNoteId,
            @Body DeliveryNote deliveryNote
    );

    @GET("/api/v1/sell/getDetailDeliveryBill/{deliveryNoteId}")
    Call<ResponseExportDetails> getExportDetails(@Path("deliveryNoteId") int deliveryNoteId);

    @POST("/api/v1/sell/createDetailDeliveryBill/{deliveryNoteId}")
    Call<ResponseObject> upsertReceiptImportDetail(
            @Header("Authorization") String token,
            @Path("deliveryNoteId") int deliveryNoteId,
            @Body ExportDetailsUpsertInfo info);
}
