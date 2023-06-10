package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Receipt implements Serializable {
    public static final boolean STATUS_DONE = true;
    public static final boolean STATUS_NOT_YET = false;

    @SerializedName("MaPhieuNhap")
    private int receiptId;
    @SerializedName("NhanVien")
    private Employee employee;
    @SerializedName("DonDatHang")
    private OrderImport orderImport;
    @SerializedName("TrangThai")
    private boolean status;
    @SerializedName("NgayNhap")
    private String inputDateTime;

    // create receipt
    public Receipt(OrderImport orderImport, boolean status, String inputDateTime) {
        this.orderImport = orderImport;
        this.status = status;
        this.inputDateTime = inputDateTime;
    }

    // get all receipt
    public Receipt(int receiptId, Employee employee, OrderImport orderImport, boolean status, String inputDateTime) {
        this.receiptId = receiptId;
        this.employee = employee;
        this.orderImport = orderImport;
        this.status = status;
        this.inputDateTime = inputDateTime;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public OrderImport getOrder() {
        return orderImport;
    }

    public boolean getStatus() {
        return status;
    }

    public String getInputDateTime() {
        return inputDateTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
