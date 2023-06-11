package com.example.warehousemanagementwkeeper.model;

import java.util.ArrayList;

public class ResponseDeliveryNotes extends ResponseObject {
    private ArrayList<DeliveryNote> data;

    public ResponseDeliveryNotes(boolean success, String message, ArrayList<DeliveryNote> data) {
        super(success, message);
        this.data = data;
    }

    public ArrayList<DeliveryNote> getData() {
        return data;
    }
}
