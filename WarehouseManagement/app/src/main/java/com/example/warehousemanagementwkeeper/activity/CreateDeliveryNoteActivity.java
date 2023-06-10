package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api.DeliveryNoteApi;
import com.example.warehousemanagementwkeeper.api_instance.DeliveryNoteApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.OrderExport;
import com.example.warehousemanagementwkeeper.model.OrderExportDetail;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.model.ResponseOrderExportDetails;
import com.example.warehousemanagementwkeeper.my_control.AuthorizationSingleton;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.OrderExportDetailAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateDeliveryNoteActivity extends AppCompatActivity {
    public static final String TAG_ORDER_SELECTED = "TAG_ORDER_SELECTED";
    public static final int REQUEST_CREATED_DELIVERY_NOTE = 273;
    private boolean isInStockAvailable = false;
    private OrderExport orderSelected;
    private ImageButton btnBack;
    private TextView tvInputDate, tvInputTime, tvOrderDate, tvEmployeeName, tvCustomerName,
            tvCustomerAddress, tvCustomerPhone, tvCreateDeliveryNote;
    private RadioButton radGoodsDelivery;
    private EditText edtExportReason;
    private RecyclerView rvOrderDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_delivery_note);

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
        tvCustomerName = findViewById(R.id.tvSupplierName);
        tvCustomerAddress = findViewById(R.id.tvSupplierAddress);
        tvCustomerPhone = findViewById(R.id.tvSupplierPhone);
        tvCreateDeliveryNote = findViewById(R.id.tvCreateDeliveryNote);

        radGoodsDelivery = findViewById(R.id.radGoodsDelivery);

        edtExportReason = findViewById(R.id.edtExportReason);

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
        tvCreateDeliveryNote.setOnClickListener(view -> {
            if (isInStockAvailable){
                setDataRvOrderDetail(orderSelected); // recheck in stock
                if (isInStockAvailable){
                    createDeliveryNote();
                }
                else {
                    showDialogWarning();
                }
            }
            else {
                showDialogWarning();
            }
        });
    }

    private void setData() {
        orderSelected = (OrderExport) getIntent().getSerializableExtra(TAG_ORDER_SELECTED);
        if (orderSelected != null){
            Date date = new Date();
            tvInputDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(date));
            tvInputTime.setText(new SimpleDateFormat("HH:mm").format(date));
            tvOrderDate.setText(orderSelected.getOderDate());
            tvEmployeeName.setText(orderSelected.getEmployee().getFullName());
            tvCustomerName.setText(orderSelected.getCustomer().getName());
            tvCustomerAddress.setText(orderSelected.getCustomer().getAddress());
            tvCustomerPhone.setText(orderSelected.getCustomer().getPhone());
            setDataRvOrderDetail(orderSelected);
        }
    }

    private void setDataRvOrderDetail(OrderExport orderSelected) {
        Call<ResponseOrderExportDetails> call = OrderApiInstance.getInstance().getOrderExportDetails(orderSelected.getId());
        call.enqueue(new Callback<ResponseOrderExportDetails>() {
            @Override
            public void onResponse(Call<ResponseOrderExportDetails> call, Response<ResponseOrderExportDetails> response) {
                if (response.isSuccessful()){
                    ArrayList<OrderExportDetail> orderExportDetails = response.body().getData();
                    OrderExportDetailAdapter adapter = new OrderExportDetailAdapter(CreateDeliveryNoteActivity.this, orderExportDetails);
                    rvOrderDetail.setAdapter(adapter);
                    checkInStock(orderExportDetails);
                }
                else {
                    try {
                        Toast.makeText(CreateDeliveryNoteActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOrderExportDetails> call, Throwable t) {
                Toast.makeText(CreateDeliveryNoteActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void checkInStock(ArrayList<OrderExportDetail> orderExportDetails) {
        isInStockAvailable = false;
        for (int i=0; i<orderExportDetails.size(); i++){
            if (orderExportDetails.get(i).getQuantity() > orderExportDetails.get(i).getItem().getInStock()){
                Toast.makeText(
                        this,
                        getText(R.string.warning_do_not_have_enough).toString().concat(" " + orderExportDetails.get(i).getItem().getName()),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            if (i == orderExportDetails.size()-1){
                isInStockAvailable = true;
                Toast.makeText(this, R.string.in_stock_available, Toast.LENGTH_SHORT).show();
            }
        }
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

    private void showDialogWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(R.string.warning_create_delivery_note_title)
                .setMessage(R.string.warning_create_delivery_note_message)
                .setPositiveButton(R.string.btn_submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createDeliveryNote();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    private void createDeliveryNote(){
        String inputDate = tvInputDate.getText().toString();
        // dd-mm-yyyy to yyyy-mm-dd
        inputDate = StringFormatFacade.toDatabaseDate(inputDate);
        String inputDateTime = inputDate + " " + tvInputTime.getText().toString();
        String exportReason = edtExportReason.getText().toString();
        String exportType = radGoodsDelivery.isChecked() ? DeliveryNote.TYPE_EXPORT_NORMAL : DeliveryNote.TYPE_EXPORT_DESTROY;

        DeliveryNote deliveryNote = new DeliveryNote(
                false,
                inputDateTime,
                exportReason,
                exportType
        );

        Call<ResponseObject> call = DeliveryNoteApiInstance.getInstance().createDeliveryNote(
                AuthorizationSingleton.getInstance().getBearerToken(),
                orderSelected.getId(),
                deliveryNote
        );
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CreateDeliveryNoteActivity.this, R.string.success_create_delivery_note, Toast.LENGTH_SHORT).show();
                    CreateDeliveryNoteActivity.this.setResult(RESULT_OK);
                    CreateDeliveryNoteActivity.this.finish();
                }
                else {
                    try {
//                        Toast.makeText(CreateDeliveryNoteActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                        Log.e("error", response.errorBody().string());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(CreateDeliveryNoteActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}