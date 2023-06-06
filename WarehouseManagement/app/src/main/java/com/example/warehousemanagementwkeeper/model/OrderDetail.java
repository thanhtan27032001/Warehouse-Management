package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("MaDonDH")
    private int orderId;
    @SerializedName("MaMatHang")
    private int itemId;
    @SerializedName("TenMatHang")
    private String itemName;
    @SerializedName("SoLuong")
    private int quantity;
    @SerializedName("DonGia")
    private double price;

    // get order detail
    public OrderDetail(int orderId, int itemId, String itemName, int quantity, double price) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
