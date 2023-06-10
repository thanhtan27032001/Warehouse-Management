package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.SelectOrderImportActivity;
import com.example.warehousemanagementwkeeper.model.OrderImport;

import java.util.ArrayList;

public class OrdersImportAdapter extends RecyclerView.Adapter<OrdersImportAdapter.ViewHolder> {
    private SelectOrderImportActivity context;
    private ArrayList<OrderImport> orderImports;

    public OrdersImportAdapter(SelectOrderImportActivity context, ArrayList<OrderImport> orderImports) {
        this.context = context;
        this.orderImports = orderImports;
    }

    @NonNull
    @Override
    public OrdersImportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_import, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersImportAdapter.ViewHolder holder, int position) {
        OrderImport orderImport = orderImports.get(position);
        holder.tvOrderId.setText(context.getText(R.string.tv_oderId).toString().concat(String.valueOf(orderImport.getId())));
        holder.tvSupplierName.setText(orderImport.getSupplier().getName());
        holder.tvOrderDate.setText(orderImport.getOderDate());
        holder.tvEmployeeName.setText(orderImport.getEmployee().getFullName()
                .concat(" #")
                .concat(String.valueOf(orderImport.getEmployee().getId()))
        );
        holder.cardOrder.setOnClickListener(view -> {
//            context.showDialogCreateReceipt(order);
            context.openCreateReceiptActivity(orderImport);
        });
    }

    @Override
    public int getItemCount() {
        return orderImports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cardOrder;
        private TextView tvOrderId, tvSupplierName, tvOrderDate, tvEmployeeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardOrder = itemView.findViewById(R.id.cardOrder);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tvSupplierName = itemView.findViewById(R.id.tvSupplierName);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
        }
    }
}
