package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class ImportDetail {
    @SerializedName("MaPhieuNhap")
    private int receiptId;
    @SerializedName("MatHang")
    private Item item;
    @SerializedName("SoLuong")
    private int quantity;
    @SerializedName("DonGia")
    private double price;

    // get import detail
    public ImportDetail(int receiptId, Item item, int quantity, double price) {
        this.receiptId = receiptId;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
