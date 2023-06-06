package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseOrderDetails extends ResponseObject{
    private ArrayList<OrderDetail> data;

    public ResponseOrderDetails(boolean success, String message, ArrayList<OrderDetail> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<OrderDetail> getData() {
        return data;
    }
}
