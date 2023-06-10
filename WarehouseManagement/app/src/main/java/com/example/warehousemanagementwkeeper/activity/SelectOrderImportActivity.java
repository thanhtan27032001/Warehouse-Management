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
import com.example.warehousemanagementwkeeper.model.OrderImport;
import com.example.warehousemanagementwkeeper.model.ResponseOrdersImport;
import com.example.warehousemanagementwkeeper.rv_adapter.OrdersImportAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectOrderImportActivity extends AppCompatActivity {
    public static final int REQUEST_NEW_RECEIPT_CREATED = 2703;
    private ImageButton btnBack;
    private RecyclerView rvOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_import);

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
                setResult(RESULT_OK);
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
        Call<ResponseOrdersImport> call = OrderApiInstance.getInstance().getAllOrdersImport();
        call.enqueue(new Callback<ResponseOrdersImport>() {
            @Override
            public void onResponse(Call<ResponseOrdersImport> call, Response<ResponseOrdersImport> response) {
                if (response.isSuccessful()){
                    ArrayList<OrderImport> orderImports = new ArrayList<>();
                    for (OrderImport orderImport : response.body().getData()){
                        if (!orderImport.isCreatedReceipt()){
                            orderImports.add(orderImport);
                        }
                    }
                    OrdersImportAdapter adapter = new OrdersImportAdapter(SelectOrderImportActivity.this, orderImports);
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
            public void onFailure(Call<ResponseOrdersImport> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void openCreateReceiptActivity(OrderImport orderImport){
        Intent intent = new Intent(this, CreateReceiptActivity.class);
        intent.putExtra(CreateReceiptActivity.TAG_ORDER_SELECTED, orderImport);
        startActivityForResult(intent, CreateReceiptActivity.REQUEST_CREATED_RECEIPT);
    }
}