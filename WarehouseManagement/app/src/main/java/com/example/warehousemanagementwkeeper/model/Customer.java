package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {
    @SerializedName("MaKhachHang")
    private int id;
    @SerializedName("TenKhachHang")
    private String name;
    @SerializedName("DiaChi")
    private String address;
    @SerializedName("SDT")
    private String phone;

    public Customer(int id, String name, String address, String phone) {
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
