package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class ItemNotInShelf {
    @SerializedName("MatHang")
    private Item item;
    @SerializedName("SoLuongChuaLenKe")
    private int quantity;

    public ItemNotInShelf(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
