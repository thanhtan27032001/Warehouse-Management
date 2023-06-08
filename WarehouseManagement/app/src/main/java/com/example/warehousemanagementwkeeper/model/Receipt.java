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
    private Order order;
    @SerializedName("TrangThai")
    private boolean status;
    @SerializedName("NgayNhap")
    private String inputDateTime;

    // create receipt
    public Receipt(Order order, boolean status, String inputDateTime) {
        this.order = order;
        this.status = status;
        this.inputDateTime = inputDateTime;
    }

    // get all receipt
    public Receipt(int receiptId, Employee employee, Order order, boolean status, String inputDateTime) {
        this.receiptId = receiptId;
        this.employee = employee;
        this.order = order;
        this.status = status;
        this.inputDateTime = inputDateTime;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Order getOrder() {
        return order;
    }

    public boolean isStatus() {
        return status;
    }

    public String getInputDateTime() {
        return inputDateTime;
    }
}
