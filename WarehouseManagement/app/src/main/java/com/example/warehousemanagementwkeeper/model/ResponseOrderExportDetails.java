package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseOrderExportDetails extends ResponseObject{
    private ArrayList<OrderExportDetail> data;

    public ResponseOrderExportDetails(boolean success, String message, ArrayList<OrderExportDetail> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<OrderExportDetail> getData() {
        return data;
    }
}
