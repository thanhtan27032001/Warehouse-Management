package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class ImportDetail implements Cloneable{
    @SerializedName("MaPhieuNhap")
    private int receiptId;
    @SerializedName("MatHang")
    private Item item;
    @SerializedName("SoLuong")
    private int quantity;
    @SerializedName("DonGia")
    private long price;
    private int quantityOrder;
    private long priceOrder;

    // get import detail
    public ImportDetail(int receiptId, Item item, int quantity, long price) {
        this.receiptId = receiptId;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }
    // add to import detail not existed
    public ImportDetail(int receiptId, Item item, int quantity, long price, int quantityOrder, long priceOrder) {
        this.receiptId = receiptId;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.quantityOrder = quantityOrder;
        this.priceOrder = priceOrder;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public long getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(long priceOrder) {
        this.priceOrder = priceOrder;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
