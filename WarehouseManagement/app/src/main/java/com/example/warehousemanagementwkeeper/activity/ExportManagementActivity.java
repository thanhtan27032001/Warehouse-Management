package com.example.warehousemanagementwkeeper.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.DeliveryNoteApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseDeliveryNotes;
import com.example.warehousemanagementwkeeper.model.ResponseReceipts;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.DeliveryNoteAdapter;
import com.example.warehousemanagementwkeeper.rv_adapter.ReceiptAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExportManagementActivity extends AppCompatActivity {
    private boolean isDoneReceiptShown = true;
    private ArrayList<DeliveryNote> deliveryNotes;
    private ArrayList<DeliveryNote> shownDeliveryNotes;

    private ImageButton btnBack, btnMore;
    private RecyclerView rvDeliveryNote;
    private SwipeRefreshLayout swfDeliveryNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_management);
        setView();
        setEvent();
        setData();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK){
//            if (requestCode == SelectOrderImportActivity.REQUEST_NEW_RECEIPT_CREATED){
//                setData();
//            }
//            else if (requestCode == ReceiptDetailActivity.REQUEST_RECEIPT_IS_UPDATED){
//                setData();
//            }
//        }
//    }

    private void setView() {
        btnBack = findViewById(R.id.btnBack);
        btnMore = findViewById(R.id.btnMore);

        rvDeliveryNote = findViewById(R.id.rvDeliveryNote);
        rvDeliveryNote.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        swfDeliveryNote = findViewById(R.id.swipeRefresh);
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
        btnMore.setOnClickListener(view -> showMoreMenu());
        swfDeliveryNote.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });
    }

    private void setData() {
        Call<ResponseDeliveryNotes> call = DeliveryNoteApiInstance.getInstance().getAllDeliveryNotes();
        call.enqueue(new Callback<ResponseDeliveryNotes>() {
            @Override
            public void onResponse(Call<ResponseDeliveryNotes> call, Response<ResponseDeliveryNotes> response) {
                swfDeliveryNote.setRefreshing(false);
                if (response.isSuccessful()){
                    deliveryNotes = new ArrayList<>();
                    for (DeliveryNote deliveryNote: response.body().getData()){
                        deliveryNotes.add(0, deliveryNote);
                    }
                    showHideDoneReceipt();
//                    ReceiptAdapter adapter = new ReceiptAdapter(ImportManagementActivity.this, receipts);
//                    rvReceipt.setAdapter(adapter);
                }
                else {
                    try {
                        Toast.makeText(ExportManagementActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDeliveryNotes> call, Throwable t) {
                swfDeliveryNote.setRefreshing(false);
                Toast.makeText(ExportManagementActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void viewReceiptDetail(DeliveryNote deliveryNote){
        Intent intent = new Intent(this, ReceiptDetailActivity.class);
        intent.putExtra(ReceiptDetailActivity.TAG_RECEIPT_SELECTED, deliveryNote);
        startActivityForResult(intent, ReceiptDetailActivity.REQUEST_RECEIPT_IS_UPDATED);
    }

    private void showMoreMenu() {
        PopupMenu menu = new PopupMenu(this, btnMore);
        menu.getMenuInflater().inflate(R.menu.menu_receipt_management, menu.getMenu());
        // event
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.option_create_receipt:
                        openCreateReceiptActivity();
                        break;
                    case R.id.option_show_hide_done_receipt:
                        if (deliveryNotes != null){
                            isDoneReceiptShown = !isDoneReceiptShown;
                            showHideDoneReceipt();
                        }
                        break;
                }
                return false;
            }
        });

        menu.show();
    }

    private void showHideDoneReceipt() {
        shownDeliveryNotes = new ArrayList<>();
        if (isDoneReceiptShown){ // show done receipt
            shownDeliveryNotes.addAll(deliveryNotes);
            DeliveryNoteAdapter adapter = new DeliveryNoteAdapter(ExportManagementActivity.this, shownDeliveryNotes);
            rvDeliveryNote.setAdapter(adapter);
        }
        else { // hide done receipt
            for (DeliveryNote deliveryNote: deliveryNotes){
                if (!deliveryNote.getStatus()){
                    shownDeliveryNotes.add(deliveryNote);
                }
            }
            DeliveryNoteAdapter adapter = new DeliveryNoteAdapter(ExportManagementActivity.this, shownDeliveryNotes);
            rvDeliveryNote.setAdapter(adapter);
        }
    }

    private void openCreateReceiptActivity() {
        Intent intent = new Intent(this, SelectOrderImportActivity.class);
        startActivityForResult(intent, SelectOrderImportActivity.REQUEST_NEW_RECEIPT_CREATED);
    }
}