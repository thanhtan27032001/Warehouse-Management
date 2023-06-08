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
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseImportDetails;
import com.example.warehousemanagementwkeeper.my_control.MyFormat;
import com.example.warehousemanagementwkeeper.rv_adapter.ImportDetailAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportDetailFragment extends Fragment {

    private Receipt receipt;
    private ArrayList<ImportDetail> importDetails;
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
        Call<ResponseImportDetails> call = ReceiptApiInstance.getInstance().getReceiptImportDetails(receipt.getReceiptId());
        call.enqueue(new Callback<ResponseImportDetails>() {
            @Override
            public void onResponse(Call<ResponseImportDetails> call, Response<ResponseImportDetails> response) {
                if (response.isSuccessful()){
                    importDetails = new ArrayList<>();
                    importDetails.addAll(response.body().getData());
                    ImportDetailAdapter adapter = new ImportDetailAdapter(ImportDetailFragment.this, importDetails);
                    rvImportDetail.setAdapter(adapter);
                    Log.e("test", response.body().getData().size() + "");
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
}