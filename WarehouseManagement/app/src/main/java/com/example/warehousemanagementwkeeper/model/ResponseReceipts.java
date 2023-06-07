package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseReceipts extends ResponseObject {
    private ArrayList<Receipt> data;

    public ResponseReceipts(boolean success, String message, ArrayList<Receipt> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<Receipt> getData() {
        return data;
    }
}
