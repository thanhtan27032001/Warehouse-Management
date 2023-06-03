package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class LoginInfo {
    @SerializedName("TaiKhoan")
    private String username;
    @SerializedName("MatKhau")
    private String password;

    public LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
