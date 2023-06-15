package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.ItemApiInstance;
import com.example.warehousemanagementwkeeper.model.ItemNotInShelf;
import com.example.warehousemanagementwkeeper.model.PutItemToShelfInfo;
import com.example.warehousemanagementwkeeper.model.ResponseItemsNotInShelf;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.rv_adapter.ItemNotInShelfAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PutItemToShelfActivity extends AppCompatActivity {

    private RecyclerView rvItemNotOnShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_item_to_shelf);

        setView();
        setEvent();
        setData();

    }

    private void setView() {
        rvItemNotOnShelf = findViewById(R.id.rvItemNotOnShelf);
        rvItemNotOnShelf.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setEvent() {

    }

    private void setData() {
        Call<ResponseItemsNotInShelf> call = ItemApiInstance.getInstance().getItemsNotInShelf();
        call.enqueue(new Callback<ResponseItemsNotInShelf>() {
            @Override
            public void onResponse(Call<ResponseItemsNotInShelf> call, Response<ResponseItemsNotInShelf> response) {
                if (response.isSuccessful()){
                    ArrayList<ItemNotInShelf> itemsNotInShelf = response.body().getData();
                    ItemNotInShelfAdapter adapter = new ItemNotInShelfAdapter(PutItemToShelfActivity.this, itemsNotInShelf);
                    rvItemNotOnShelf.setAdapter(adapter);
                }
                else {
                    try {
                        Toast.makeText(PutItemToShelfActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseItemsNotInShelf> call, Throwable t) {
                Toast.makeText(PutItemToShelfActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void showDialogPutItemToShelf(ItemNotInShelf item){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_put_item_to_shelf);

        LinearLayout layout = dialog.findViewById(R.id.layoutPutItemToShelf);
        TextView btnSubmit = dialog.findViewById(R.id.btnSubmit);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView tvItemName = dialog.findViewById(R.id.tvItemName);
        TextView tvItemInStock = dialog.findViewById(R.id.tvItemInStock);
        TextView tvItemId = dialog.findViewById(R.id.edtItemId);
        EditText edtBoxId = dialog.findViewById(R.id.edtBoxId);
        EditText edtItemQuantity = dialog.findViewById(R.id.edtItemQuantity);
//        ImageButton btnScanBarcodeItem = dialog.findViewById(R.id.btnScanBarcodeItem);
        ImageButton btnScanBarcodeBox = dialog.findViewById(R.id.btnScanBarcodeBox);
        ImageButton btnAdd = dialog.findViewById(R.id.btnAdd);
        ImageButton btnMinus = dialog.findViewById(R.id.btnMinus);

        // Adjust dialog width fit to screen
        try {
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = (int)(getResources().getDisplayMetrics().widthPixels*0.85);
            layout.requestLayout();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // event
        tvItemName.setText(item.getItem().getName());
        tvItemInStock.setText(String.valueOf(item.getItem().getInStock()));
        tvItemId.setText(item.getItem().getItemId());
        edtItemQuantity.setText(String.valueOf(item.getQuantity()));

        btnCancel.setOnClickListener(view -> dialog.dismiss());
        btnSubmit.setOnClickListener(view -> {
            String itemId = tvItemId.getText().toString().trim();
            String boxName = edtBoxId.getText().toString().trim();
            int quantity = Integer.parseInt(edtItemQuantity.getText().toString());
            if (putItemToShelfInfoValidated(itemId, boxName, quantity)){
                PutItemToShelfInfo info = new PutItemToShelfInfo(itemId, boxName, quantity);
                putItemToShelf(info);
            }
        });

        dialog.show();
    }

    private void putItemToShelf(PutItemToShelfInfo info) {
        Call<ResponseObject> call = ItemApiInstance.getInstance().putItemToShelf(info);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(PutItemToShelfActivity.this, R.string.success_put_item_to_shelf, Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Toast.makeText(PutItemToShelfActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(PutItemToShelfActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private boolean putItemToShelfInfoValidated(String itemId, String boxName, int quantity) {
        if (itemId.equals("")){
            Toast.makeText(this, R.string.warning_miss_item_id, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (boxName.equals("")){
            Toast.makeText(this, R.string.warning_miss_box_id, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (quantity < 1){
            Toast.makeText(this, R.string.warning_quantity_invalid, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}