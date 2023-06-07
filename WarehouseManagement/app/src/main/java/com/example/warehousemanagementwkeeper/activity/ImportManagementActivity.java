package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.warehousemanagementwkeeper.R;

public class ImportManagementActivity extends AppCompatActivity {

    private RecyclerView rvReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_management);
    }
}