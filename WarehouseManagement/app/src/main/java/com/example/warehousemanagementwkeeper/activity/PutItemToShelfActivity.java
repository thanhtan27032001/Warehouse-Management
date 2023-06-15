package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.warehousemanagementwkeeper.R;

public class PutItemToShelfActivity extends AppCompatActivity {

    private RecyclerView rvItemNotOnShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_item_to_shelf);

        setView();
        setEvent();
        setData();

    }

    private void setView() {
        rvItemNotOnShelf = findViewById(R.id.rvItemNotOnShelf);
    }

    private void setEvent() {

    }

    private void setData() {

    }
}