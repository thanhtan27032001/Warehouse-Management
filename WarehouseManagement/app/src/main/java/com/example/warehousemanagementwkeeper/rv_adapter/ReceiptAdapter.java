package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.ImportManagementActivity;
import com.example.warehousemanagementwkeeper.model.Receipt;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    private ImportManagementActivity context;
    private ArrayList<Receipt> receipts;

    public ReceiptAdapter(ImportManagementActivity context, ArrayList<Receipt> receipts) {
        this.context = context;
        this.receipts = receipts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptAdapter.ViewHolder holder, int position) {
        Receipt receipt = receipts.get(position);
        holder.tvReceiptId.setText("#".concat(String.valueOf(receipt.getReceiptId())));
        holder.tvSupplierName.setText(receipt.getOrder().getSupplier().getName());
        holder.tvInputDate.setText(receipt.getInputDateTime());
        holder.tvOrderId.setText("#".concat(String.valueOf(receipt.getOrder().getId())));
        holder.tvEmployeeName.setText(receipt.getEmployee().getFullName());
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvReceiptId, tvSupplierName, tvInputDate, tvOrderId, tvEmployeeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReceiptId = itemView.findViewById(R.id.tvReceiptId);
            tvSupplierName = itemView.findViewById(R.id.tvSupplierName);
            tvInputDate = itemView.findViewById(R.id.tvInputDate);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
        }
    }
}
