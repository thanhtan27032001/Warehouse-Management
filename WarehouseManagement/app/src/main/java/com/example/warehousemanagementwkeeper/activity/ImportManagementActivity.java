package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseReceipts;
import com.example.warehousemanagementwkeeper.my_control.MyFormat;
import com.example.warehousemanagementwkeeper.rv_adapter.ReceiptAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportManagementActivity extends AppCompatActivity {

    private ArrayList<Receipt> receipts;
    private ImageButton btnBack;
    private RecyclerView rvReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_management);
        setView();
        setEvent();
        setData();
    }

    private void setView() {
        btnBack = findViewById(R.id.btnBack);

        rvReceipt = findViewById(R.id.rvReceipt);
        rvReceipt.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
    }

    private void setData() {
        Call<ResponseReceipts> call = ReceiptApiInstance.getInstance().getAllReceipt();
        call.enqueue(new Callback<ResponseReceipts>() {
            @Override
            public void onResponse(Call<ResponseReceipts> call, Response<ResponseReceipts> response) {
                if (response.isSuccessful()){
                    receipts = new ArrayList<>();
                    receipts.addAll(response.body().getData());
                    ReceiptAdapter adapter = new ReceiptAdapter(ImportManagementActivity.this, receipts);
                    rvReceipt.setAdapter(adapter);
                }
                else {
                    try {
                        Toast.makeText(ImportManagementActivity.this, MyFormat.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseReceipts> call, Throwable t) {
                Toast.makeText(ImportManagementActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}