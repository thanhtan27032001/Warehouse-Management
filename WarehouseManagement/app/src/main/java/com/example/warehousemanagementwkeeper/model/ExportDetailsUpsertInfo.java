package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExportDetailsUpsertInfo {
    @SerializedName("MatHang")
    private ArrayList<ExportDetail> exportDetails;

    public ExportDetailsUpsertInfo(ArrayList<ExportDetail> importDetails) {
        this.exportDetails = importDetails;
    }
}
