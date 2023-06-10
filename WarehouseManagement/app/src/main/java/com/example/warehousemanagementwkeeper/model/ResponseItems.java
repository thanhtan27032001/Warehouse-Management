package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseItems extends ResponseObject{
    private ArrayList<Item> data;

    public ResponseItems(boolean success, String message, ArrayList<Item> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<Item> getData() {
        return data;
    }
}
