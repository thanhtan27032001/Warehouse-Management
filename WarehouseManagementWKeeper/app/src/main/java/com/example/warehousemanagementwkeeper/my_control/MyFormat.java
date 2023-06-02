package com.example.warehousemanagementwkeeper.my_control;

public class MyFormat {
    public static String getBearerToken(String token){
        return "Bearer " + token;
    }

    public static String getError(String Body){
        return Body.substring(Body.indexOf("message")+10, Body.indexOf("\",\"data\""));
    }
}
