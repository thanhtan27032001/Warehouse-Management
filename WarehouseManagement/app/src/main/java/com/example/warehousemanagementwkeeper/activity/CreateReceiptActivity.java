package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.model.Order;

public class CreateReceiptActivity extends AppCompatActivity {

    public static final String TAG_ORDER_SELECTED = "TAG_ORDER_SELECTED";
    private Order orderSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);

        setView();
        setEvent();
        setData();
    }

    private void setView() {

    }

    private void setEvent() {

    }

    private void setData() {
        orderSelected = (Order) getIntent().getSerializableExtra(TAG_ORDER_SELECTED);
        if (orderSelected != null){
            Toast.makeText(this, String.valueOf(orderSelected.getId()), Toast.LENGTH_SHORT).show();
        }
    }
}