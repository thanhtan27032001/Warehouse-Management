package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseOrderImportDetails extends ResponseObject{
    private ArrayList<OrderImportDetail> data;

    public ResponseOrderImportDetails(boolean success, String message, ArrayList<OrderImportDetail> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<OrderImportDetail> getData() {
        return data;
    }
}
