package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderExport implements Serializable {
    @SerializedName("MaDonDHX")
    private int id;
    @SerializedName("NgayDatHang")
    private String oderDate;
    @SerializedName("KhachHang")
    private Customer customer;
    @SerializedName("NhanVien")
    private Employee employee;
    @SerializedName("DaTaoPhieu")
    private boolean isCreatedDeliveryNote;

    // get order
    public OrderExport(int id, String oderDate, Customer customer, Employee employee, boolean isCreatedDeliveryNote) {
        this.id = id;
        this.oderDate = oderDate;
        this.customer = customer;
        this.employee = employee;
        this.isCreatedDeliveryNote = isCreatedDeliveryNote;
    }

    public int getId() {
        return id;
    }

    public String getOderDate() {
        return oderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isCreatedDeliveryNote() {
        return isCreatedDeliveryNote;
    }
}
