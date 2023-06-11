package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {
    @SerializedName("MaMatHang")
    private String itemId;
    @SerializedName("TenMatHang")
    private String name;
    @SerializedName("SoLuongTon")
    private int inStock;
    @SerializedName("isActive")
    private boolean isActive;
    @SerializedName("MaLoaiHang")
    private int typeId;
    @SerializedName("MaNhaCC")
    private int supplierId;

    // get import detail
    public Item(String itemId, String name, int inStock, boolean isActive, int typeId, int supplierId) {
        this.itemId = itemId;
        this.name = name;
        this.inStock = inStock;
        this.isActive = isActive;
        this.typeId = typeId;
        this.supplierId = supplierId;
    }

    // add to import detail not existed
    public Item(String itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getInStock() {
        return inStock;
    }
}
