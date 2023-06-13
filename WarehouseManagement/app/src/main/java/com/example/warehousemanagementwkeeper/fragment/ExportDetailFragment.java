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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.BarcodeScannerActivity;
import com.example.warehousemanagementwkeeper.api_instance.DeliveryNoteApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.OrderApiInstance;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.ExportDetail;
import com.example.warehousemanagementwkeeper.model.ExportDetailsUpsertInfo;
import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.model.ImportDetailsUpsertInfo;
import com.example.warehousemanagementwkeeper.model.Item;
import com.example.warehousemanagementwkeeper.model.OrderExportDetail;
import com.example.warehousemanagementwkeeper.model.ResponseExportDetails;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.model.ResponseOrderExportDetails;
import com.example.warehousemanagementwkeeper.my_control.AuthorizationSingleton;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.example.warehousemanagementwkeeper.rv_adapter.ExportDetailAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExportDetailFragment extends Fragment {
    private DeliveryNote deliveryNote;
    private ArrayList<ExportDetail> exportDetails;
    private ArrayList<OrderExportDetail> orderExportDetails;
    private ExportDetailAdapter exportDetailAdapter;
    private RecyclerView rvExportDetail;
    private FloatingActionButton btnScanBarcode;

    public ExportDetailFragment(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import_detail, container, false);

        rvExportDetail = view.findViewById(R.id.rvImportDetail);
        rvExportDetail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        btnScanBarcode = view.findViewById(R.id.btnScanBarcode);

        setEvent();
        setData();

        return view;
    }

    private void setEvent() {
        btnScanBarcode.setOnClickListener(view -> {
//            scanBarcode();
        });
    }

    private void setData() {
        Call<ResponseOrderExportDetails> call1 = OrderApiInstance.getInstance().getOrderExportDetails(deliveryNote.getOrder().getId());
        call1.enqueue(new Callback<ResponseOrderExportDetails>() {
            @Override
            public void onResponse(Call<ResponseOrderExportDetails> call, Response<ResponseOrderExportDetails> response) {
                if (response.isSuccessful()){
                    orderExportDetails = new ArrayList<>();
                    orderExportDetails.addAll(response.body().getData());
                    Log.e("order detail", response.body().getData().size() + "");
                    Call<ResponseExportDetails> call2 = DeliveryNoteApiInstance.getInstance().getExportDetails(deliveryNote.getDeliveryNoteId());
                    call2.enqueue(new Callback<ResponseExportDetails>() {
                        @Override
                        public void onResponse(Call<ResponseExportDetails> call, Response<ResponseExportDetails> response) {
                            if (response.isSuccessful()){
                                ArrayList<ExportDetail> temp = response.body().getData();
                                exportDetails = new ArrayList<>();
                                boolean isImported;
                                for (OrderExportDetail orderExportDetail : orderExportDetails){
                                    isImported = false;
                                    for (ExportDetail exportDetail: temp){
                                        if (orderExportDetail.getItem().getItemId().equals(exportDetail.getItem().getItemId())){
                                            exportDetail.setQuantityOrder(orderExportDetail.getQuantity());
                                            exportDetail.setPriceOrder(orderExportDetail.getPrice());
                                            exportDetails.add(exportDetail);
                                            temp.remove(exportDetail);
                                            isImported = true;
                                            break;
                                        }
                                    }
                                    if (!isImported){
                                        exportDetails.add(new ExportDetail(
                                                deliveryNote.getDeliveryNoteId(),
                                                new Item(orderExportDetail.getItem().getItemId(), orderExportDetail.getItem().getName()),
                                                0,
                                                orderExportDetail.getPrice(),
                                                orderExportDetail.getQuantity(),
                                                orderExportDetail.getPrice()));
                                    }
                                }
                                exportDetailAdapter = new ExportDetailAdapter(ExportDetailFragment.this, exportDetails);
                                rvExportDetail.setAdapter(exportDetailAdapter);
                                Log.e("import detail", response.body().getData().size() + "");
                            }
                            else if (response.code() == 400) { // not import any order
                                exportDetails = new ArrayList<>();
                                for (OrderExportDetail orderExportDetail : orderExportDetails){
                                    exportDetails.add(new ExportDetail(
                                            deliveryNote.getDeliveryNoteId(),
                                            new Item(orderExportDetail.getItem().getItemId(), orderExportDetail.getItem().getName()),
                                            0,
                                            orderExportDetail.getPrice(),
                                            orderExportDetail.getQuantity(),
                                            orderExportDetail.getPrice()));
                                }
                                exportDetailAdapter = new ExportDetailAdapter(ExportDetailFragment.this, exportDetails);
                                rvExportDetail.setAdapter(exportDetailAdapter);
                                Log.e("import detail", exportDetails.size() + "");
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
                        public void onFailure(Call<ResponseExportDetails> call, Throwable t) {
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
            public void onFailure(Call<ResponseOrderExportDetails> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), R.string.error_500, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void upsertExportDetail(ExportDetail exportDetail, int newQuantity, long newPrice, @Nullable Dialog dialog){
        try {
            ExportDetail temp = (ExportDetail) exportDetail.clone();
            temp.setQuantity(newQuantity);
            temp.setPrice(newPrice);
            ArrayList<ExportDetail> body = new ArrayList<>();
            body.add(temp);
            Call<ResponseObject> call = DeliveryNoteApiInstance.getInstance().upsertReceiptImportDetail(
                    AuthorizationSingleton.getInstance().getBearerToken(),
                    deliveryNote.getDeliveryNoteId(),
                    new ExportDetailsUpsertInfo(body)
            );
            call.enqueue(new Callback<ResponseObject>() {
                @Override
                public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                    if (response.isSuccessful()){
                        exportDetail.setQuantity(newQuantity);
                        exportDetail.setPrice(newPrice);
                        exportDetailAdapter.notifyItemChanged(exportDetails.indexOf(exportDetail));
                        if (dialog != null){
                            dialog.dismiss();
                        }
                    }
                    else {
                        exportDetailAdapter.notifyItemChanged(exportDetails.indexOf(exportDetail));
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
                    exportDetailAdapter.notifyItemChanged(exportDetails.indexOf(exportDetail));
                    Toast.makeText(getContext(), R.string.error_500, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void showDialogExportDetail(ExportDetail exportDetail) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_import_detail);

        LinearLayout layout = dialog.findViewById(R.id.layoutImportDetail);
        TextView tvItemName = dialog.findViewById(R.id.tvItemName);
        TextView tvItemId = dialog.findViewById(R.id.tvItemId);
        TextView tvOrderPrice = dialog.findViewById(R.id.tvOrderPrice);
        TextView tvOrderQuantity = dialog.findViewById(R.id.tvOrderQuantity);
        TextView edtImportPrice = dialog.findViewById(R.id.edtImportPrice);
        TextView edtImportQuantity = dialog.findViewById(R.id.edtImportQuantity);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnSubmit = dialog.findViewById(R.id.btnSubmit);

        // set data
        tvItemName.setText(exportDetail.getItem().getName());
        tvItemId.setText(String.valueOf(exportDetail.getItem().getItemId()));
        tvOrderPrice.setText(StringFormatFacade.getStringPrice(exportDetail.getPriceOrder()));
        tvOrderQuantity.setText(String.valueOf(exportDetail.getQuantityOrder()));
        edtImportPrice.setText(String.valueOf(exportDetail.getPrice()));
        edtImportQuantity.setText(String.valueOf(exportDetail.getQuantity()));

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
        edtImportQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (edtImportQuantity.getText().toString().trim().equals("")){
                    edtImportQuantity.setText("0");
                }
                else if (Integer.parseInt(edtImportQuantity.getText().toString()) > exportDetail.getQuantityOrder()){
                    edtImportQuantity.setText(String.valueOf(exportDetail.getQuantityOrder()));
                }
                return false;
            }
        });
        edtImportPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (edtImportPrice.getText().toString().trim().equals("")){
                    edtImportPrice.setText("0");
                }
                return false;
            }
        });
        btnCancel.setOnClickListener(view -> dialog.dismiss());
        btnSubmit.setOnClickListener(view -> {
            int newQuantity = Integer.parseInt(edtImportQuantity.getText().toString());
            long newPrice = Long.parseLong(edtImportPrice.getText().toString());
            upsertExportDetail(exportDetail, newQuantity, newPrice, dialog);
        });

        dialog.show();
    }

//    private void scanBarcode() {
//        ScanOptions options = new ScanOptions();
//        options.setPrompt(getText(R.string.scanner_prompt).toString());
//        options.setBeepEnabled(true);
//        options.setOrientationLocked(true);
//        options.setCaptureActivity(BarcodeScannerActivity.class);
//        barcodeLauncher.launch(options);
//    }
//
//    ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
//            new ScanContract(),
//            new ActivityResultCallback<ScanIntentResult>() {
//                @Override
//                public void onActivityResult(ScanIntentResult result) {
//                    if (result.getContents() != null){
//                        searchExportDetailByItemId(result.getContents());
//                        Log.e("test", result.getContents());
//                    }
//                }
//            }
//    );

//    private void searchExportDetailByItemId(String itemId) {
//        for (int i = 0; i< exportDetails.size(); i++){
//            if (exportDetails.get(i).getItem().getItemId().equals(itemId)){
//                showDialogImportDetail(exportDetails.get(i));
//                break;
//            }
//            if (i == exportDetails.size()-1){
//                Toast.makeText(getContext(), R.string.warning_item_import_detail_not_found, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}