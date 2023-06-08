package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseImportDetails extends ResponseObject{
    private ArrayList<ImportDetail> data;

    public ResponseImportDetails(boolean success, String message, ArrayList<ImportDetail> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<ImportDetail> getData() {
        return data;
    }
}
