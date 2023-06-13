package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseExportDetails extends ResponseObject{
    private ArrayList<ExportDetail> data;

    public ResponseExportDetails(boolean success, String message, ArrayList<ExportDetail> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<ExportDetail> getData() {
        return data;
    }
}
