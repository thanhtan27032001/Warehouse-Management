package com.example.warehousemanagementwkeeper.my_control;

public class MyFormat {
//    public static String getBearerToken(String token){
//        return "Bearer " + token;
//    }

    public static String getError(String Body){
        return Body.substring(Body.indexOf("message")+10, Body.indexOf("\",\"data\""));
    }

    public static String getDateOnly(String date){
        // date format dd-mm-yyyy hh:mm:ss
        return date.substring(0, 10);
    }

    public static String getTimeOnly(String date){
        // date format dd-mm-yyyy hh:mm:ss
        return date.substring(11);
    }

    public static String toDatabaseDate(String date){
        // dd-mm-yyyy to yyyy-mm-dd
        return date.substring(6) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2);
    }
}
