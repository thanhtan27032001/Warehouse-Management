package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.fragment.ImportDetailFragment;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.ExportDetail;
import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;

import java.util.ArrayList;

public class ImportDetailAdapter extends RecyclerView.Adapter<ImportDetailAdapter.ViewHolder> {

    private ImportDetailFragment context;
    private ArrayList<ImportDetail> importDetails;
    private boolean receiptStatus;

    public ImportDetailAdapter(ImportDetailFragment context, ArrayList<ImportDetail> importDetails, boolean receiptStatus) {
        this.context = context;
        this.importDetails = importDetails;
        this.receiptStatus = receiptStatus;
    }

    @NonNull
    @Override
    public ImportDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_import_export_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImportDetailAdapter.ViewHolder holder, int position) {
        ImportDetail exportDetail = importDetails.get(position);
        if (position % 2 == 0){
            holder.layoutItem.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
            holder.btnAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
            holder.btnMinus.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
        }
        else {
            holder.layoutItem.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
            holder.btnAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
            holder.btnMinus.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
        }
        holder.tvName.setText(exportDetail.getItem().getName());
        holder.tvId.setText(String.valueOf(exportDetail.getItem().getItemId()));
        holder.edtQuantity.setText(String.valueOf(exportDetail.getQuantity()));
        holder.tvQuantity.setText(String.valueOf(exportDetail.getQuantity()));
        holder.tvOrderQuantity.setText(String.valueOf(exportDetail.getQuantityOrder()));
        holder.tvPrice.setText(StringFormatFacade.getStringPrice(exportDetail.getPrice()));

        // disable edit
        if (receiptStatus == DeliveryNote.STATUS_DONE){
            holder.btnAdd.setVisibility(View.GONE);
            holder.btnMinus.setVisibility(View.GONE);
            holder.edtQuantity.setVisibility(View.GONE);
            holder.tvQuantity.setVisibility(View.VISIBLE);
        }
        else {
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
            holder.edtQuantity.setVisibility(View.VISIBLE);
            holder.tvQuantity.setVisibility(View.GONE);
            // event edit
            holder.btnAdd.setOnClickListener(view -> {
                if (exportDetail.getQuantity()+1 <= exportDetail.getQuantityOrder()){
                    context.upsertImportDetail(exportDetail, exportDetail.getQuantity()+1, exportDetail.getPrice(), null);
                }
                else {
                    Toast.makeText(context.getContext(), R.string.warning_maximum_quantity, Toast.LENGTH_SHORT).show();
                }
            });
            holder.btnMinus.setOnClickListener(view -> {
                if (exportDetail.getQuantity()-1 >= 0){
                    context.upsertImportDetail(exportDetail, exportDetail.getQuantity()-1, exportDetail.getPrice(), null);
                }
                else {
                    Toast.makeText(context.getContext(), R.string.warning_minimum_quantity, Toast.LENGTH_SHORT).show();
                }
            });
            holder.edtQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    Toast.makeText(context.getContext(), "Done", Toast.LENGTH_SHORT).show();
                    if (holder.edtQuantity.getText().toString().trim().equals("")){
                        holder.edtQuantity.setText("0");
                    }
                    else if (Integer.parseInt(holder.edtQuantity.getText().toString()) > exportDetail.getQuantityOrder()){
                        holder.edtQuantity.setText(String.valueOf(exportDetail.getQuantityOrder()));
                    }
                    context.upsertImportDetail(exportDetail, Integer.parseInt(holder.edtQuantity.getText().toString()), exportDetail.getPrice(), null);
                    return false;
                }
            });
        }

        holder.layoutItem.setOnClickListener(view -> {
            context.showDialogImportDetail(exportDetail);
        });
    }

    @Override
    public int getItemCount() {
        return importDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutItem;
        private TextView tvName, tvId, tvOrderQuantity, tvPrice, tvQuantity;
        private EditText edtQuantity;
        private ImageView btnAdd, btnMinus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvId = itemView.findViewById(R.id.tvItemId);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvItemQuantity);
            edtQuantity = itemView.findViewById(R.id.edtItemQuantity);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
