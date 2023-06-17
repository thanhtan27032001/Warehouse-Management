package com.example.warehousemanagementwkeeper.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.BarcodeScannerActivity;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.model.ImportDetailsUpsertInfo;
import com.example.warehousemanagementwkeeper.model.Item;
import com.example.warehousemanagementwkeeper.model.OrderImportDetail;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseImportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.model.ResponseOrderImportDetails;
import com.example.warehousemanagementwkeeper.my_control.AuthorizationSingleton;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.ImportDetailAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportDetailFragment extends Fragment {

    private Receipt receipt;
    private ArrayList<ImportDetail> importDetails;
    private ArrayList<OrderImportDetail> orderImportDetails;
    private ImportDetailAdapter importDetailAdapter;
    private RecyclerView rvImportDetail;
    private FloatingActionButton btnScanBarcode;

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

        btnScanBarcode = view.findViewById(R.id.btnScanBarcode);

        setEvent();
        setData();

        return view;
    }

    private void setEvent() {
        btnScanBarcode.setOnClickListener(view -> {
            scanBarcode();
//            if (PermissionCheckerFacade.checkCameraPermission(getActivity())){
//                scanBarcode();
//            }
//            else {
////                requestCameraPermission();
//            }
        });
    }

    private void setData() {
        Call<ResponseOrderImportDetails> call1 = OrderApiInstance.getInstance().getOrderImportDetails(receipt.getOrder().getId());
        call1.enqueue(new Callback<ResponseOrderImportDetails>() {
            @Override
            public void onResponse(Call<ResponseOrderImportDetails> call, Response<ResponseOrderImportDetails> response) {
                if (response.isSuccessful()){
                    orderImportDetails = new ArrayList<>();
                    orderImportDetails.addAll(response.body().getData());
                    Log.e("order detail", response.body().getData().size() + "");
                    Call<ResponseImportDetails> call2 = ReceiptApiInstance.getInstance().getReceiptImportDetails(receipt.getReceiptId());
                    call2.enqueue(new Callback<ResponseImportDetails>() {
                        @Override
                        public void onResponse(Call<ResponseImportDetails> call, Response<ResponseImportDetails> response) {
                            if (response.isSuccessful()){
                                ArrayList<ImportDetail> temp = response.body().getData();
                                importDetails = new ArrayList<>();
                                boolean isImported;
                                for (OrderImportDetail orderImportDetail : orderImportDetails){
                                    isImported = false;
                                    for (ImportDetail importDetail: temp){
                                        if (orderImportDetail.getItem().getItemId().equals(importDetail.getItem().getItemId())){
                                            importDetail.setQuantityOrder(orderImportDetail.getQuantity());
                                            importDetail.setPriceOrder(orderImportDetail.getPrice());
                                            importDetails.add(importDetail);
                                            temp.remove(importDetail);
                                            isImported = true;
                                            break;
                                        }
                                    }
                                    if (!isImported){
                                        importDetails.add(new ImportDetail(
                                                receipt.getReceiptId(),
                                                new Item(orderImportDetail.getItem().getItemId(), orderImportDetail.getItem().getName()),
                                                0,
                                                orderImportDetail.getPrice(),
                                                orderImportDetail.getQuantity(),
                                                orderImportDetail.getPrice()));
                                    }
                                }
                                importDetailAdapter = new ImportDetailAdapter(ImportDetailFragment.this, importDetails, receipt.getStatus());
                                rvImportDetail.setAdapter(importDetailAdapter);
                                Log.e("import detail", response.body().getData().size() + "");
                            }
                            else if (response.code() == 400) { // not import any order
                                importDetails = new ArrayList<>();
                                for (OrderImportDetail orderImportDetail : orderImportDetails){
                                    importDetails.add(new ImportDetail(
                                            receipt.getReceiptId(),
                                            new Item(orderImportDetail.getItem().getItemId(), orderImportDetail.getItem().getName()),
                                            0,
                                            orderImportDetail.getPrice(),
                                            orderImportDetail.getQuantity(),
                                            orderImportDetail.getPrice()));
                                }
                                importDetailAdapter = new ImportDetailAdapter(ImportDetailFragment.this, importDetails, receipt.getStatus());
                                rvImportDetail.setAdapter(importDetailAdapter);
                                Log.e("import detail", importDetails.size() + "");
                            }
                            else {
                                try {
                                    Toast.makeText(getContext(), StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOrderImportDetails> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), R.string.error_500, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void upsertImportDetail(ImportDetail importDetail, int newQuantity, long newPrice, @Nullable Dialog dialog){
        try {
            ImportDetail temp = (ImportDetail) importDetail.clone();
            temp.setQuantity(newQuantity);
            temp.setPrice(newPrice);
            ArrayList<ImportDetail> body = new ArrayList<>();
            body.add(temp);
            Call<ResponseObject> call = ReceiptApiInstance.getInstance().upsertReceiptImportDetail(
                    AuthorizationSingleton.getInstance().getBearerToken(),
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
                        if (dialog != null){
                            dialog.dismiss();
                        }
                    }
                    else {
                        importDetailAdapter.notifyItemChanged(importDetails.indexOf(importDetail));
                        try {
                            Toast.makeText(getContext(), StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
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

    public void showDialogImportDetail(ImportDetail importDetail) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_import_detail);

        LinearLayout layout = dialog.findViewById(R.id.layoutImportDetail);
        TextView tvItemName = dialog.findViewById(R.id.tvItemName);
        TextView tvItemId = dialog.findViewById(R.id.tvItemId);
        TextView tvOrderPrice = dialog.findViewById(R.id.tvOrderPrice);
        TextView tvOrderQuantity = dialog.findViewById(R.id.tvOrderQuantity);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnSubmit = dialog.findViewById(R.id.btnSubmit);
        TextView btnOk = dialog.findViewById(R.id.btnOk);
        TextView tvImExportPrice = dialog.findViewById(R.id.tvImExportPrice);
        TextView tvImExportQuantity = dialog.findViewById(R.id.tvImExportQuantity);
        TextView tvImExportPriceValue = dialog.findViewById(R.id.tvImExportPriceValue);
        TextView tvImExportQuantityValue = dialog.findViewById(R.id.tvImExportQuantityValue);

        EditText edtImExportPrice = dialog.findViewById(R.id.edtImExportPrice);
        EditText edtImExportQuantity = dialog.findViewById(R.id.edtImExportQuantity);

        // set data
        tvItemName.setText(importDetail.getItem().getName());
        tvItemId.setText(String.valueOf(importDetail.getItem().getItemId()));
        tvOrderPrice.setText(StringFormatFacade.getStringPrice(importDetail.getPriceOrder()));
        tvOrderQuantity.setText(String.valueOf(importDetail.getQuantityOrder()));
        tvImExportPrice.setText(R.string.tv_import_price);
        tvImExportQuantity.setText(R.string.tv_import_quantity);
        tvImExportPriceValue.setText(String.valueOf(importDetail.getPrice()));
        tvImExportQuantityValue.setText(String.valueOf(importDetail.getQuantity()));
        edtImExportPrice.setText(String.valueOf(importDetail.getPrice()));
        edtImExportQuantity.setText(String.valueOf(importDetail.getQuantity()));

        // disable edit
        if (receipt.getStatus() == DeliveryNote.STATUS_DONE){
            edtImExportPrice.setVisibility(View.GONE);
            edtImExportQuantity.setVisibility(View.GONE);
            tvImExportPriceValue.setVisibility(View.VISIBLE);
            tvImExportQuantityValue.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            btnOk.setVisibility(View.VISIBLE);
        }
        else {
            edtImExportPrice.setVisibility(View.VISIBLE);
            edtImExportQuantity.setVisibility(View.VISIBLE);
            tvImExportPriceValue.setVisibility(View.GONE);
            tvImExportQuantityValue.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            btnOk.setVisibility(View.GONE);
        }

        // Adjust dialog width fit to screen
        try {
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = (int)(getResources().getDisplayMetrics().widthPixels*0.85);
            layout.requestLayout();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        // set dialog background transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // event
        edtImExportQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (edtImExportQuantity.getText().toString().trim().equals("")){
                    edtImExportQuantity.setText("0");
                }
                else if (Integer.parseInt(edtImExportQuantity.getText().toString()) > importDetail.getQuantityOrder()){
                    edtImExportQuantity.setText(String.valueOf(importDetail.getQuantityOrder()));
                }
                return false;
            }
        });
        edtImExportPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (edtImExportPrice.getText().toString().trim().equals("")){
                    edtImExportPrice.setText("0");
                }
                return false;
            }
        });
        btnCancel.setOnClickListener(view -> dialog.dismiss());
        btnOk.setOnClickListener(view -> dialog.dismiss());
        btnSubmit.setOnClickListener(view -> {
            int newQuantity = Integer.parseInt(edtImExportQuantity.getText().toString());
            long newPrice = Long.parseLong(edtImExportPrice.getText().toString());
            upsertImportDetail(importDetail, newQuantity, newPrice, dialog);
        });

        dialog.show();
    }

    private void scanBarcode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt(getText(R.string.scanner_prompt).toString());
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(BarcodeScannerActivity.class);
        barcodeLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
            new ScanContract(),
            new ActivityResultCallback<ScanIntentResult>() {
                @Override
                public void onActivityResult(ScanIntentResult result) {
                    if (result.getContents() != null){
                        searchImportDetailByItemId(result.getContents());
                        Log.e("test", result.getContents());
                    }
                }
            }
    );

    private void searchImportDetailByItemId(String itemId) {
        for (int i=0; i<importDetails.size(); i++){
            if (importDetails.get(i).getItem().getItemId().equals(itemId)){
                showDialogImportDetail(importDetails.get(i));
                break;
            }
            if (i == importDetails.size()-1){
                Toast.makeText(getContext(), R.string.warning_item_import_detail_not_found, Toast.LENGTH_SHORT).show();
            }
        }
    }
}