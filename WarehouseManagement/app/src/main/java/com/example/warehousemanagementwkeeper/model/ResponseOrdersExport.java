package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseOrdersExport extends ResponseObject{
    private ArrayList<OrderExport> data;

    public ResponseOrdersExport(boolean success, String message, ArrayList<OrderExport> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<OrderExport> getData() {
        return data;
    }
}
