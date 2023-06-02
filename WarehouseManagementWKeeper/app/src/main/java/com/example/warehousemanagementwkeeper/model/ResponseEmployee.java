package com.example.warehousemanagementwkeeper.model;

public class ResponseEmployee extends ResponseObject{
    private Employee data;

    public ResponseEmployee(boolean success, String message, Employee data) {
        super(success, message);
        this.data = data;
    }

    public Employee getData() {
        return data;
    }
}
