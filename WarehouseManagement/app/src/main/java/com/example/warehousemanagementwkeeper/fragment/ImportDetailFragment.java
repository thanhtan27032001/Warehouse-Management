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
import com.example.warehousemanagementwkeeper.model.ImportDetailsUpsertInfo;
import com.example.warehousemanagementwkeeper.model.Item;
import com.example.warehousemanagementwkeeper.model.Order;
import com.example.warehousemanagementwkeeper.model.OrderDetail;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseImportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.model.ResponseOrderDetails;
import com.example.warehousemanagementwkeeper.my_control.MyAuthorization;
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
    private ImportDetailAdapter importDetailAdapter;

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
                                importDetailAdapter = new ImportDetailAdapter(ImportDetailFragment.this, importDetails);
                                rvImportDetail.setAdapter(importDetailAdapter);
                                Log.e("import detail", response.body().getData().size() + "");
                            }
                            else if (response.code() == 400) { // not import any order
                                importDetails = new ArrayList<>();
                                for (OrderDetail orderDetail: orderDetails){
                                    importDetails.add(new ImportDetail(
                                            receipt.getReceiptId(),
                                            new Item(orderDetail.getItemId(), orderDetail.getItemName()),
                                            0,
                                            orderDetail.getPrice(),
                                            orderDetail.getQuantity(),
                                            orderDetail.getPrice()));
                                }
                                importDetailAdapter = new ImportDetailAdapter(ImportDetailFragment.this, importDetails);
                                rvImportDetail.setAdapter(importDetailAdapter);
                                Log.e("import detail", importDetails.size() + "");
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

    public void upsertImportDetail(ImportDetail importDetail, int newQuantity, double newPrice){
        try {
            ImportDetail temp = (ImportDetail) importDetail.clone();
            temp.setQuantity(newQuantity);
            temp.setPrice(newPrice);
            ArrayList<ImportDetail> body = new ArrayList<>();
            body.add(temp);
            Call<ResponseObject> call = ReceiptApiInstance.getInstance().upsertReceiptImportDetail(
                    MyAuthorization.getInstance().getBearerToken(),
                    receipt.getReceiptId(),
                    new ImportDetailsUpsertInfo(body)
            );
            call.enqueue(new Callback<ResponseObject>() {
                @Override
                public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                    if (response.isSuccessful()){
                        importDetail.setQuantity(newQuantity);
                        importDetail.setPrice(newPrice);
                        importDetailAdapter.notifyItemChanged(importDetails.indexOf(importDetail));
//                        Toast.makeText(getContext(), R.string.success_upsert_import_detail, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        importDetailAdapter.notifyItemChanged(importDetails.indexOf(importDetail));
                        try {
                            Toast.makeText(getContext(), MyFormat.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject> call, Throwable t) {
                    importDetailAdapter.notifyItemChanged(importDetails.indexOf(importDetail));
                    Toast.makeText(getContext(), R.string.error_500, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}