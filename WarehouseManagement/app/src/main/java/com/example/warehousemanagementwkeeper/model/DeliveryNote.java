package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeliveryNote implements Serializable {
    public static final boolean STATUS_DONE = true;
    public static final boolean STATUS_NOT_YET = false;
    public static final String TYPE_EXPORT_NORMAL = "XH";
    public static final String TYPE_EXPORT_DESTROY = "TH";
    @SerializedName("MaPhieuXuat")
    private int deliveryNoteId;
    @SerializedName("NhanVien")
    private Employee employee;
    @SerializedName("DonDatHangXuat")
    private OrderExport orderExport;
    @SerializedName("DaNhanHang")
    private boolean status;
    @SerializedName("NgayXuat")
    private String exportDateTime;
    @SerializedName("LyDoXuat")
    private String reason;
    @SerializedName("LoaiPX")
    private String exportType;

    // create delivery note
    public DeliveryNote(boolean status, String exportDateTime, String reason, String exportType) {
        this.status = status;
        this.exportDateTime = exportDateTime;
        this.reason = reason;
        this.exportType = exportType;
    }

    // get all delivery note
    public DeliveryNote(int deliveryNoteId, Employee employee, OrderExport orderExport, boolean status, String exportDateTime, String reason, String exportType) {
        this.deliveryNoteId = deliveryNoteId;
        this.employee = employee;
        this.orderExport = orderExport;
        this.status = status;
        this.exportDateTime = exportDateTime;
        this.reason = reason;
        this.exportType = exportType;
    }

    public int getDeliveryNoteId() {
        return deliveryNoteId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public OrderExport getOrder() {
        return orderExport;
    }

    public boolean getStatus() {
        return status;
    }

    public String getExportDateTime() {
        return exportDateTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
