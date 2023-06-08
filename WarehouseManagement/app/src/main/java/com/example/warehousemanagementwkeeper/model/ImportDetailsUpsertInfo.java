package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImportDetailsUpsertInfo {
    @SerializedName("MatHang")
    private ArrayList<ImportDetail> importDetails;

    public ImportDetailsUpsertInfo(ArrayList<ImportDetail> importDetails) {
        this.importDetails = importDetails;
    }
}
