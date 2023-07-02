package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.ItemApiInstance;
import com.example.warehousemanagementwkeeper.model.Item;
import com.example.warehousemanagementwkeeper.model.ResponseItems;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.ItemAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllItemsActivity extends AppCompatActivity {

    private RecyclerView rvAllItems;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_items);

        setView();
        setEvent();
        setData();

    }

    private void setView() {
        btnBack = findViewById(R.id.btnBack);

        rvAllItems = findViewById(R.id.rvAllItems);
        rvAllItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void setData() {
        Call<ResponseItems> call = ItemApiInstance.getInstance().getAllItems();
        call.enqueue(new Callback<ResponseItems>() {
            @Override
            public void onResponse(Call<ResponseItems> call, Response<ResponseItems> response) {
                if (response.isSuccessful()){
                    ArrayList<Item> items = new ArrayList<>();
                    items.addAll(response.body().getData());
                    ItemAdapter adapter = new ItemAdapter(ViewAllItemsActivity.this, items);
                    rvAllItems.setAdapter(adapter);
                }
                else {
                    try {
                        Toast.makeText(ViewAllItemsActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseItems> call, Throwable t) {
                Toast.makeText(ViewAllItemsActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void viewItemDetail(Item item) {
        Intent intent = new Intent(this, ItemStockActivity.class);
        intent.putExtra(ItemStockActivity.TAG_SELECTED_ITEM, item);
        startActivity(intent);
    }
}