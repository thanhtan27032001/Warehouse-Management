package com.example.warehousemanagementwkeeper.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api.ItemApi;
import com.example.warehousemanagementwkeeper.api_instance.ItemApiInstance;
import com.example.warehousemanagementwkeeper.model.Item;
import com.example.warehousemanagementwkeeper.model.ResponseItems;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.SearchedItemAdapter;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckItemStockActivity extends AppCompatActivity {

    ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
            new ScanContract(),
            new ActivityResultCallback<ScanIntentResult>() {
                @Override
                public void onActivityResult(ScanIntentResult result) {
                    if (result.getContents() != null){
                        String content = result.getContents();
                        edtSearchItem.setText(result.getContents());
                        searchItem(content);
                    }
                }
            }
    );

    private ImageButton btnBack;
    private EditText edtSearchItem;
    private ImageView btnScanBarcode;
    private RecyclerView rvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item_stock);

        setView();
        setEvent();
        setData();

    }

    private void setView() {
        btnBack = findViewById(R.id.btnBack);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        edtSearchItem = findViewById(R.id.edtSearchItem);

        rvItem = findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
        btnScanBarcode.setOnClickListener(view -> scanBarcode());
        edtSearchItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String content = edtSearchItem.getText().toString();
                searchItem(content);
                return false;
            }
        });
    }

    private void setData() {

    }

    private void scanBarcode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt(getText(R.string.scanner_prompt).toString());
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(BarcodeScannerActivity.class);
        barcodeLauncher.launch(options);
    }

    public void searchItem(String content){
        if (StringFormatFacade.isBarcode(content)){
            searchItemByBarcode(content);
        }
        else {
            searchItemByName(content);
        }
    }
    private void searchItemByBarcode(String content) {

    }

    private void searchItemByName(String itemName) {
        Call<ResponseItems> call = ItemApiInstance.getInstance().getItemsByName(itemName);
        call.enqueue(new Callback<ResponseItems>() {
            @Override
            public void onResponse(Call<ResponseItems> call, Response<ResponseItems> response) {
                if (response.isSuccessful()){
                    ArrayList<Item> items = response.body().getData();
                    SearchedItemAdapter adapter = new SearchedItemAdapter(CheckItemStockActivity.this, items);
                    rvItem.setAdapter(adapter);
                    rvItem.setVisibility(View.VISIBLE);
                }
                else {
                    try {
                        Toast.makeText(CheckItemStockActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseItems> call, Throwable t) {
                Toast.makeText(CheckItemStockActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void showResult(Item item) {

    }
}