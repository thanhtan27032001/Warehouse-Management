package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.ItemApiInstance;
import com.example.warehousemanagementwkeeper.model.Item;
import com.example.warehousemanagementwkeeper.model.ItemLocation;
import com.example.warehousemanagementwkeeper.model.ResponseItemLocations;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.ItemLocationAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            //
            setDataRv();
        }
    }

    private void setDataRv() {
        Call<ResponseItemLocations> call = ItemApiInstance.getInstance().getItemLocations(item.getItemId());
        call.enqueue(new Callback<ResponseItemLocations>() {
            @Override
            public void onResponse(Call<ResponseItemLocations> call, Response<ResponseItemLocations> response) {
                if (response.isSuccessful()){
                    ArrayList<ItemLocation> itemLocations = response.body().getData();
                    ItemLocationAdapter adapter = new ItemLocationAdapter(ItemStockActivity.this, itemLocations);
                    rvItemLocation.setAdapter(adapter);
                }
                else {
                    try {
                        Toast.makeText(ItemStockActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseItemLocations> call, Throwable t) {
                Toast.makeText(ItemStockActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}