package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseOrdersImport extends ResponseObject{
    private ArrayList<OrderImport> data;

    public ResponseOrdersImport(boolean success, String message, ArrayList<OrderImport> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<OrderImport> getData() {
        return data;
    }
}
