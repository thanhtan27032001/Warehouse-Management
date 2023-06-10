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
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseReceipts;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.ReceiptAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportManagementActivity extends AppCompatActivity {

    private boolean isDoneReceiptShown = true;
    private ArrayList<Receipt> receipts;
    private ArrayList<Receipt> shownReceipts;

    private ImageButton btnBack, btnMore;
    private RecyclerView rvReceipt;
    private SwipeRefreshLayout swfReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_management);
        setView();
        setEvent();
        setData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == SelectOrderImportActivity.REQUEST_NEW_RECEIPT_CREATED){
                setData();
            }
            else if (requestCode == ReceiptDetailActivity.REQUEST_RECEIPT_IS_UPDATED){
                setData();
            }
        }
    }

    private void setView() {
        btnBack = findViewById(R.id.btnBack);
        btnMore = findViewById(R.id.btnMore);

        rvReceipt = findViewById(R.id.rvReceipt);
        rvReceipt.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        swfReceipt = findViewById(R.id.swipeRefresh);
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
        btnMore.setOnClickListener(view -> showMoreMenu());
        swfReceipt.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });
    }

    private void setData() {
        Call<ResponseReceipts> call = ReceiptApiInstance.getInstance().getAllReceipt();
        call.enqueue(new Callback<ResponseReceipts>() {
            @Override
            public void onResponse(Call<ResponseReceipts> call, Response<ResponseReceipts> response) {
                swfReceipt.setRefreshing(false);
                if (response.isSuccessful()){
                    receipts = new ArrayList<>();
                    for (Receipt receipt: response.body().getData()){
                        receipts.add(0, receipt);
                    }
                    showHideDoneReceipt();
//                    ReceiptAdapter adapter = new ReceiptAdapter(ImportManagementActivity.this, receipts);
//                    rvReceipt.setAdapter(adapter);
                }
                else {
                    try {
                        Toast.makeText(ImportManagementActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseReceipts> call, Throwable t) {
                swfReceipt.setRefreshing(false);
                Toast.makeText(ImportManagementActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void viewReceiptDetail(Receipt receipt){
        Intent intent = new Intent(this, ReceiptDetailActivity.class);
        intent.putExtra(ReceiptDetailActivity.TAG_RECEIPT_SELECTED, receipt);
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
                        if (receipts != null){
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
        shownReceipts = new ArrayList<>();
        if (isDoneReceiptShown){ // show done receipt
            shownReceipts.addAll(receipts);
            ReceiptAdapter adapter = new ReceiptAdapter(ImportManagementActivity.this, shownReceipts);
            rvReceipt.setAdapter(adapter);
        }
        else { // hide done receipt
            for (Receipt receipt: receipts){
                if (!receipt.getStatus()){
                    shownReceipts.add(receipt);
                }
            }
            ReceiptAdapter adapter = new ReceiptAdapter(ImportManagementActivity.this, shownReceipts);
            rvReceipt.setAdapter(adapter);
        }
    }

    private void openCreateReceiptActivity() {
        Intent intent = new Intent(this, SelectOrderImportActivity.class);
        startActivityForResult(intent, SelectOrderImportActivity.REQUEST_NEW_RECEIPT_CREATED);
    }


}