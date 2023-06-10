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
import com.example.warehousemanagementwkeeper.model.OrderExport;
import com.example.warehousemanagementwkeeper.model.ResponseOrdersExport;
import com.example.warehousemanagementwkeeper.rv_adapter.OrdersExportAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectOrderExportActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_DELIVERY_NOTE_CREATED = 2703;
    private ImageButton btnBack;
    private RecyclerView rvOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_export);

        setView();
        setEvent();
        setData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Log.e("test", "result ok");
            if (requestCode == CreateDeliveryNoteActivity.REQUEST_CREATED_DELIVERY_NOTE){
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
        Call<ResponseOrdersExport> call = OrderApiInstance.getInstance().getAllOrdersExport();
        call.enqueue(new Callback<ResponseOrdersExport>() {
            @Override
            public void onResponse(Call<ResponseOrdersExport> call, Response<ResponseOrdersExport> response) {
                if (response.isSuccessful()){
                    ArrayList<OrderExport> orderExports = new ArrayList<>();
                    for (OrderExport orderExport : response.body().getData()){
                        if (!orderExport.isCreatedDeliveryNote()){
                            orderExports.add(orderExport);
                        }
                    }
                    OrdersExportAdapter adapter = new OrdersExportAdapter(SelectOrderExportActivity.this, orderExports);
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
            public void onFailure(Call<ResponseOrdersExport> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void openCreateDeliveryNoteActivity(OrderExport orderExport){
        Intent intent = new Intent(this, CreateDeliveryNoteActivity.class);
        intent.putExtra(CreateDeliveryNoteActivity.TAG_ORDER_SELECTED, orderExport);
        startActivityForResult(intent, CreateDeliveryNoteActivity.REQUEST_CREATED_DELIVERY_NOTE);
    }
}