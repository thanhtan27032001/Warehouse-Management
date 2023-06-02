package com.example.warehousemanagementwkeeper.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagementwkeeper.model.Employee;

public class MainActivityFactory {
    public static Intent getActivityIntent(AppCompatActivity context, String role){
        switch (role){
            case Employee.ROLE_EMPLOYEE:
                return new Intent(context, MainEmployeeActivity.class);
            case Employee.ROLE_WAREHOUSE_KEEPER:
                return new Intent(context, MainWarehouseKeeperActivity.class);
            case Employee.ROLE_ACCOUNTANT:
                return new Intent(context, MainAccountantActivity.class);
            default:
                return null;
        }
    }
}
