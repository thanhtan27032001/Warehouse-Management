package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseItemLocations extends ResponseObject{
    private ArrayList<ItemLocation> data;

    public ResponseItemLocations(boolean success, String message, ArrayList<ItemLocation> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<ItemLocation> getData() {
        return data;
    }
}
