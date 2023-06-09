package com.example.warehousemanagementwkeeper.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.model.Order;
import com.example.warehousemanagementwkeeper.model.ResponseOrders;
import com.example.warehousemanagementwkeeper.rv_adapter.OrdersAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectOrderReceiptActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_receipt);

        setView();
        setEvent();
        setData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Log.e("test", "result ok");
            if (requestCode == CreateReceiptActivity.REQUEST_CREATED_RECEIPT){
                Log.e("test", "request create");
                setData();
            }
        }
    }

    private void setView() {
        btnBack = findViewById(R.id.btnBack);

        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
    }

    private void setData() {
        Call<ResponseOrders> call = OrderApiInstance.getInstance().getAllOrders();
        call.enqueue(new Callback<ResponseOrders>() {
            @Override
            public void onResponse(Call<ResponseOrders> call, Response<ResponseOrders> response) {
                if (response.isSuccessful()){
                    ArrayList<Order> orders = new ArrayList<>();
                    for (Order order: response.body().getData()){
                        if (!order.isCreatedReceipt()){
                            orders.add(order);
                        }
                    }
                    OrdersAdapter adapter = new OrdersAdapter(SelectOrderReceiptActivity.this, orders);
                    rvOrders.setAdapter(adapter);
                }
                else {
                    try {
                        Log.e("error", response.errorBody().string());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOrders> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void openCreateReceiptActivity(Order order){
        Intent intent = new Intent(this, CreateReceiptActivity.class);
        intent.putExtra(CreateReceiptActivity.TAG_ORDER_SELECTED, order);
        startActivityForResult(intent, CreateReceiptActivity.REQUEST_CREATED_RECEIPT);
    }
}