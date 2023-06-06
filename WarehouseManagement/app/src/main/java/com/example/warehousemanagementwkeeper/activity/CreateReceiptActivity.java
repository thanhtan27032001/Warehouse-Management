package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.model.Order;
import com.example.warehousemanagementwkeeper.model.OrderDetail;
import com.example.warehousemanagementwkeeper.model.ResponseOrderDetails;
import com.example.warehousemanagementwkeeper.my_control.MyFormat;
import com.example.warehousemanagementwkeeper.rv_adapter.OrderDetailAdapter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReceiptActivity extends AppCompatActivity {

    public static final String TAG_ORDER_SELECTED = "TAG_ORDER_SELECTED";
    private Order orderSelected;
    private ImageButton btnBack;
    private TextView tvOrderName, tvOrderDate, tvEmployeeName, tvSupplyName, tvSupplyAddress, tvSupplyPhone;
    private RecyclerView rvOrderDetail;
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

        tvOrderName = findViewById(R.id.tvOrderName);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvEmployeeName = findViewById(R.id.tvEmployeeName);
        tvSupplyName = findViewById(R.id.tvSupplyName);
        tvSupplyAddress = findViewById(R.id.tvSupplyAddress);
        tvSupplyPhone = findViewById(R.id.tvSupplyPhone);

        rvOrderDetail = findViewById(R.id.rvOrderDetail);
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
    }

    private void setData() {
        orderSelected = (Order) getIntent().getSerializableExtra(TAG_ORDER_SELECTED);
        if (orderSelected != null){
            Toast.makeText(this, String.valueOf(orderSelected.getId()), Toast.LENGTH_SHORT).show();
            tvOrderDate.setText(orderSelected.getOderDate());
            tvEmployeeName.setText(orderSelected.getEmployee().getFullName());
            tvSupplyName.setText(orderSelected.getSupplier().getName());
            tvSupplyAddress.setText(orderSelected.getSupplier().getAddress());
            tvSupplyPhone.setText(orderSelected.getSupplier().getPhone());
            setDataRvOrderDetail(orderSelected);
        }
    }

    private void setDataRvOrderDetail(Order orderSelected) {
        Call<ResponseOrderDetails> call = OrderApiInstance.getInstance().getOrderDetails(orderSelected.getId());
        call.enqueue(new Callback<ResponseOrderDetails>() {
            @Override
            public void onResponse(Call<ResponseOrderDetails> call, Response<ResponseOrderDetails> response) {
                if (response.isSuccessful()){
                    ArrayList<OrderDetail> orderDetails = response.body().getData();
                    OrderDetailAdapter adapter = new OrderDetailAdapter(CreateReceiptActivity.this, orderDetails);
                    rvOrderDetail.setAdapter(adapter);
                }
                else {
                    try {
                        Toast.makeText(CreateReceiptActivity.this, MyFormat.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOrderDetails> call, Throwable t) {
                Toast.makeText(CreateReceiptActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}