package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.ExportManagementActivity;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.Receipt;

import java.util.ArrayList;

public class DeliveryNoteAdapter extends RecyclerView.Adapter<DeliveryNoteAdapter.ViewHolder> {
    private ExportManagementActivity context;
    private ArrayList<DeliveryNote> receipts;

    public DeliveryNoteAdapter(ExportManagementActivity context, ArrayList<DeliveryNote> receipts) {
        this.context = context;
        this.receipts = receipts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryNoteAdapter.ViewHolder holder, int position) {
        DeliveryNote deliveryNote = receipts.get(position);
        holder.tvReceiptId.setText(context.getText(R.string.tv_delivery_note).toString().concat(" #".concat(String.valueOf(deliveryNote.getDeliveryNoteId()))));
        if (deliveryNote.getStatus() == Receipt.STATUS_DONE){
            holder.tvReceiptId.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getDrawable(R.drawable.baseline_done_24), null);
            holder.tvReceiptId.setBackgroundColor(context.getColor(R.color.primary_light));
        }
        else {
            holder.tvReceiptId.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getDrawable(R.drawable.baseline_input_24), null);
            holder.tvReceiptId.setBackgroundColor(context.getColor(R.color.complementary_light));
        }
        holder.tvSupplierName.setText(deliveryNote.getOrder().getCustomer().getName());
        holder.tvInputDate.setText(deliveryNote.getExportDateTime());
        holder.tvOrderId.setText(context.getText(R.string.tv_order).toString().concat(" #".concat(String.valueOf(deliveryNote.getOrder().getId()))));
        holder.tvEmployeeName.setText(deliveryNote.getEmployee().getFullName());
        holder.cardReceipt.setOnClickListener(view -> context.viewDeliveryNoteDetail(deliveryNote));
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cardReceipt;
        private TextView tvReceiptId, tvSupplierName, tvInputDate, tvOrderId, tvEmployeeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardReceipt = itemView.findViewById(R.id.cardReceipt);
            tvReceiptId = itemView.findViewById(R.id.tvReceiptId);
            tvSupplierName = itemView.findViewById(R.id.tvSupplierName);
            tvInputDate = itemView.findViewById(R.id.tvInputDate);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
        }
    }
}
