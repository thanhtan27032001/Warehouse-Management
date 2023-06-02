package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseOrders extends ResponseObject{
    private ArrayList<Order> data;

    public ResponseOrders(boolean success, String message, ArrayList<Order> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<Order> getData() {
        return data;
    }
}
