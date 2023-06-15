package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class PutItemToShelfInfo {
    @SerializedName("MaMatHang")
    private String itemId;
    @SerializedName("MaO")
    private String boxId;
    @SerializedName("SoLuong")
    private int quantity;

    public PutItemToShelfInfo(String itemId, String boxId, int quantity) {
        this.itemId = itemId;
        this.boxId = boxId;
        this.quantity = quantity;
    }
}
