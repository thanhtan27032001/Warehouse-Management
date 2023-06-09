package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class OrderImportDetail {
    @SerializedName("MaDonDH")
    private int orderId;
    @SerializedName("MatHang")
    private Item item;
    @SerializedName("SoLuong")
    private int quantity;
    @SerializedName("DonGia")
    private long price;

    // get order detail


    public OrderImportDetail(int orderId, Item item, int quantity, long price) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getPrice() {
        return price;
    }
}
