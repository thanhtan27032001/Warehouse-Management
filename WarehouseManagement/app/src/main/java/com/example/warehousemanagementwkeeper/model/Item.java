package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("MaMatHang")
    private int itemId;
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
    public Item(int itemId, String name, int inStock, boolean isActive, int typeId, int supplierId) {
        this.itemId = itemId;
        this.name = name;
        this.inStock = inStock;
        this.isActive = isActive;
        this.typeId = typeId;
        this.supplierId = supplierId;
    }

    // add to import detail not existed
    public Item(int itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getInStock() {
        return inStock;
    }
}
