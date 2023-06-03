package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {
    @SerializedName("MaDonDH")
    private int id;
    @SerializedName("NgayDatHang")
    private String oderDate;
    @SerializedName("NhaCungCap")
    private Supplier supplier;
    @SerializedName("NhanVien")
    private Employee employee;

    // get order
    public Order(int id, String oderDate, Supplier supplier, Employee employee) {
        this.id = id;
        this.oderDate = oderDate;
        this.supplier = supplier;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public String getOderDate() {
        return oderDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Employee getEmployee() {
        return employee;
    }
}
