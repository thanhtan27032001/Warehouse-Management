package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.model.Item;

public class ItemStockActivity extends AppCompatActivity {

    public static final String TAG_SELECTED_ITEM = "TAG_SELECTED_ITEM";
    private Item item;
    private ImageButton btnBack;
    private TextView tvItemName, tvItemId, tvItemInStock;
    private RecyclerView rvItemLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stock);
        setView();
        setEvent();
        setData();
    }

    private void setView() {
        btnBack = findViewById(R.id.btnBack);

        tvItemName = findViewById(R.id.tvItemName);
        tvItemId = findViewById(R.id.tvItemId);
        tvItemInStock = findViewById(R.id.tvItemInStock);

        rvItemLocation = findViewById(R.id.rvItemLocation);
        rvItemLocation.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
    }

    private void setData() {
        item = (Item) getIntent().getSerializableExtra(TAG_SELECTED_ITEM);
        if (item != null){
            tvItemId.setText(item.getItemId());
            tvItemName.setText(item.getName());
            tvItemInStock.setText(String.valueOf(item.getInStock()));
        }
    }
}