package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.Order;
import com.example.warehousemanagementwkeeper.model.OrderDetail;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.model.ResponseOrderDetails;
import com.example.warehousemanagementwkeeper.my_control.AuthorizationSingleton;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.OrderDetailAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReceiptActivity extends AppCompatActivity {

    public static final String TAG_ORDER_SELECTED = "TAG_ORDER_SELECTED";
    public static final int REQUEST_CREATED_RECEIPT = 273;
    private Order orderSelected;
    private ImageButton btnBack;
    private TextView tvInputDate, tvInputTime, tvOrderDate, tvEmployeeName, tvSupplyName,
            tvSupplyAddress, tvSupplyPhone, tvCreateReceipt;
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

        tvInputDate = findViewById(R.id.tvInputDate);
        tvInputTime = findViewById(R.id.tvInputTime);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvEmployeeName = findViewById(R.id.tvEmployeeName);
        tvSupplyName = findViewById(R.id.tvSupplierName);
        tvSupplyAddress = findViewById(R.id.tvSupplierAddress);
        tvSupplyPhone = findViewById(R.id.tvSupplierPhone);
        tvCreateReceipt = findViewById(R.id.tvCreateReceipt);

        rvOrderDetail = findViewById(R.id.rvOrderDetail);
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
        tvInputDate.setOnClickListener(view -> {
            showDatePickerDialog();
        });
        tvInputTime.setOnClickListener(view -> {
            showTimePickerDialog();
        });
        tvCreateReceipt.setOnClickListener(view -> createReceipt());
    }

    private void setData() {
        orderSelected = (Order) getIntent().getSerializableExtra(TAG_ORDER_SELECTED);
        if (orderSelected != null){
            Date date = new Date();
            tvInputDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
            tvInputTime.setText(new SimpleDateFormat("HH:mm").format(date));
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
                        Toast.makeText(CreateReceiptActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
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

    private void showDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i2 + "-" + (i1+1) + "-" + i;
                tvInputDate.setText(date);
            }
        });
        dialog.show();
    }
    private void showTimePickerDialog(){
        Date date = new Date();
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String time = i + ":" + i1 + ":00";
                        tvInputTime.setText(time);
                    }
                },
                date.getHours(),
                date.getMinutes(),
                true
        );
        dialog.show();
    }
    private void createReceipt(){
        String inputDate = tvInputDate.getText().toString();
        // dd-mm-yyyy to yyyy-mm-dd
        inputDate = StringFormatFacade.toDatabaseDate(inputDate);
        String inputDateTime = inputDate + " " + tvInputTime.getText().toString();
        Receipt receipt = new Receipt(orderSelected, false, inputDateTime);

        Call<ResponseObject> call = ReceiptApiInstance.getInstance().createReceipt(
                AuthorizationSingleton.getInstance().getBearerToken(),
                orderSelected.getId(),
                receipt
        );
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CreateReceiptActivity.this, R.string.success_create_receipt, Toast.LENGTH_SHORT).show();
                    CreateReceiptActivity.this.setResult(RESULT_OK);
                    CreateReceiptActivity.this.finish();
                }
                else {
                    try {
                        Toast.makeText(CreateReceiptActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(CreateReceiptActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}