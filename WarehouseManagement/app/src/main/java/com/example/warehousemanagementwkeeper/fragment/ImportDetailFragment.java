package com.example.warehousemanagementwkeeper.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api.OrderApi;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.model.Item;
import com.example.warehousemanagementwkeeper.model.Order;
import com.example.warehousemanagementwkeeper.model.OrderDetail;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseImportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseOrderDetails;
import com.example.warehousemanagementwkeeper.my_control.MyFormat;
import com.example.warehousemanagementwkeeper.rv_adapter.ImportDetailAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportDetailFragment extends Fragment {

    private Receipt receipt;
    private ArrayList<ImportDetail> importDetails;
    private ArrayList<OrderDetail> orderDetails;
    private RecyclerView rvImportDetail;

    public ImportDetailFragment(Receipt receipt) {
        this.receipt = receipt;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import_detail, container, false);

        rvImportDetail = view.findViewById(R.id.rvImportDetail);
        rvImportDetail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        setEvent();
        setData();

        return view;
    }

    private void setEvent() {

    }

    private void setData() {
        Call<ResponseOrderDetails> call1 = OrderApiInstance.getInstance().getOrderDetails(receipt.getOrder().getId());
        call1.enqueue(new Callback<ResponseOrderDetails>() {
            @Override
            public void onResponse(Call<ResponseOrderDetails> call, Response<ResponseOrderDetails> response) {
                if (response.isSuccessful()){
                    orderDetails = new ArrayList<>();
                    orderDetails.addAll(response.body().getData());
                    Log.e("order detail", response.body().getData().size() + "");
                    Call<ResponseImportDetails> call2 = ReceiptApiInstance.getInstance().getReceiptImportDetails(receipt.getReceiptId());
                    call2.enqueue(new Callback<ResponseImportDetails>() {
                        @Override
                        public void onResponse(Call<ResponseImportDetails> call, Response<ResponseImportDetails> response) {
                            if (response.isSuccessful()){
                                ArrayList<ImportDetail> temp = response.body().getData();
                                importDetails = new ArrayList<>();
//                                importDetails.addAll(response.body().getData());
                                boolean isImported;
                                for (OrderDetail orderDetail: orderDetails){
                                    isImported = false;
                                    for (ImportDetail importDetail: temp){
                                        if (orderDetail.getItemId() == importDetail.getItem().getItemId()){
                                            importDetail.setQuantityOrder(orderDetail.getQuantity());
                                            importDetail.setPriceOrder(orderDetail.getPrice());
                                            importDetails.add(importDetail);
                                            temp.remove(importDetail);
                                            isImported = true;
                                            break;
                                        }
                                    }
                                    if (!isImported){
                                        importDetails.add(new ImportDetail(
                                                receipt.getReceiptId(),
                                                new Item(orderDetail.getItemId(), orderDetail.getItemName()),
                                                0,
                                                orderDetail.getPrice(),
                                                orderDetail.getQuantity(),
                                                orderDetail.getPrice()));
                                    }
                                }
                                ImportDetailAdapter adapter = new ImportDetailAdapter(ImportDetailFragment.this, importDetails);
                                rvImportDetail.setAdapter(adapter);
                                Log.e("import detail", response.body().getData().size() + "");
                            }
                            else {
                                try {
                                    Toast.makeText(getContext(), MyFormat.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseImportDetails> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getContext(), R.string.error_500, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    try {
                        Toast.makeText(getContext(), MyFormat.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOrderDetails> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), R.string.error_500, Toast.LENGTH_SHORT).show();
            }
        });
    }
}