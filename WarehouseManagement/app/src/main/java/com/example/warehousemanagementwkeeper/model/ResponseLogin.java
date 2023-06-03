package com.example.warehousemanagementwkeeper.model;

public class ResponseLogin extends ResponseObject{
    private String data;

    public ResponseLogin(boolean success, String message, String data) {
        super(success, message);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
