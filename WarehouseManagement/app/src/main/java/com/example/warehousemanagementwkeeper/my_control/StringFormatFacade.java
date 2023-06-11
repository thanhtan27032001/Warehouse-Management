package com.example.warehousemanagementwkeeper.my_control;

import android.content.Context;
import android.content.ContextWrapper;

import com.example.warehousemanagementwkeeper.R;

import java.text.NumberFormat;
import java.util.Locale;

public class StringFormatFacade {
    private static NumberFormat moneyFormat;
    private static Locale dong;

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

    public static String getStringPrice(long price){
        if (dong == null){
            dong = new Locale("vi", "VN");
        }
        if (moneyFormat == null){
            moneyFormat = NumberFormat.getCurrencyInstance(dong);
        }
        return moneyFormat.format(price);
    }

    public static boolean isBarcode(String s){
        return s.matches("\\d+");
    }

    public static String getItemLocation(Context context, String boxId){
        // format KHU01KE01O01
        String area = context.getText(R.string.area) + " " + boxId.substring(3, 5);
        String shelf = context.getText(R.string.shelf) + " " + boxId.substring(7, 9);
        String box = context.getText(R.string.box) + " " + boxId.substring(10);
        return area + " " + shelf + " " + box;
    }
}
