package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemLocation implements Serializable {
    @SerializedName("MaO")
    private String boxId;
    @SerializedName("MaMatHang")
    private String itemId;
    @SerializedName("SKU")
    private String SKU;
    @SerializedName("SoLuongBanDau")
    private int initialQuantity;
    @SerializedName("SoLuongHienTai")
    private int currentQuantity;
    @SerializedName("NgayLenKe")
    private String storageDate;

    // get item location
    public ItemLocation(String boxId, String itemId, String SKU, int initialQuantity, int currentQuantity, String storageDate) {
        this.boxId = boxId;
        this.itemId = itemId;
        this.SKU = SKU;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.storageDate = storageDate;
    }

    public String getBoxId() {
        return boxId;
    }

    public String getSKU() {
        return SKU;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public String getStorageDate() {
        return storageDate;
    }
}
