package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class ResponseObject {
    @SerializedName("success")
    protected boolean success;
    @SerializedName("message")
    protected String message;

    public ResponseObject() {
    }

    public ResponseObject(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
