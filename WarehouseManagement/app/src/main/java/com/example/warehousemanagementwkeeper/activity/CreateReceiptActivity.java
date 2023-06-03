package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.Order;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.model.ResponseOrders;
import com.example.warehousemanagementwkeeper.my_control.MyAuthorization;
import com.example.warehousemanagementwkeeper.my_control.MyFormat;
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

    public void showDialogCreateReceipt(Order order) {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_create_receipt);
        CardView cardDialog = dialog.findViewById(R.id.cardDialog);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        TextView btnSubmit = dialog.findViewById(R.id.btnSubmit);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);

        tvTitle.setText(R.string.title_create_receipt);
        tvMessage.setText(getText(R.string.message_create_receipt).toString()
                .concat(" #")
                .concat(String.valueOf(order.getId())
                .concat("?"))
        );

        btnSubmit.setOnClickListener(view -> {
            createReceipt(order);
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(view -> dialog.dismiss());

        try {
            ViewGroup.LayoutParams params = cardDialog.getLayoutParams();
            params.width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
            cardDialog.requestLayout();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        dialog.show();
    }

    private void createReceipt(Order order) {
        Call<ResponseObject> call = ReceiptApiInstance.getInstance().createReceipt(MyFormat.getBearerToken(MyAuthorization.getInstance().getToken()), order.getId());
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CreateReceiptActivity.this, R.string.success_create_receipt, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String error = response.errorBody().string();
                        error = MyFormat.getError(error);
                        Log.e("error", error);
                        Log.e("token", MyAuthorization.getInstance().getToken());
                        Toast.makeText(CreateReceiptActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CreateReceiptActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
            }
        });
    }
}