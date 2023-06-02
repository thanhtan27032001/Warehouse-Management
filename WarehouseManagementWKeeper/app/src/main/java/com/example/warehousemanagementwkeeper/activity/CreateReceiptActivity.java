package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.model.Order;
import com.example.warehousemanagementwkeeper.model.ResponseOrders;
import com.example.warehousemanagementwkeeper.rv_adapter.OrdersAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReceiptActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private RecyclerView rvOrders;
    private ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);

        setView();
        setEvent();
        setData();

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
                    orders = new ArrayList<>();
                    orders.addAll(response.body().getData());
                    OrdersAdapter adapter = new OrdersAdapter(CreateReceiptActivity.this, orders);
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
}