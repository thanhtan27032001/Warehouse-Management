package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class Receipt {
    public static final boolean STATUS_DONE = true;
    public static final boolean STATUS_NOT_YET = false;

    private int receiptId;
    private Employee employee;
    private Order order;
    @SerializedName("TrangThai")
    private boolean status;
    @SerializedName("NgayNhap")
    private String inputDateTime;

    public Receipt(Order order, boolean status, String inputDateTime) {
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
