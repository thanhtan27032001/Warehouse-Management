package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class Supplier {
    @SerializedName("MaNhaCC")
    private int id;
    @SerializedName("TenNhaCC")
    private String name;
    @SerializedName("DiaChi")
    private String address;
    @SerializedName("SDT")
    private String phone;

    // get supplier info in order
    public Supplier(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
