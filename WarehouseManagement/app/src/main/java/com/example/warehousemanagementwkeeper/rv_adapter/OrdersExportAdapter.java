package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.SelectOrderExportActivity;
import com.example.warehousemanagementwkeeper.model.OrderExport;

import java.util.ArrayList;

public class OrdersExportAdapter extends RecyclerView.Adapter<OrdersExportAdapter.ViewHolder> {
    private SelectOrderExportActivity context;
    private ArrayList<OrderExport> orderExports;

    public OrdersExportAdapter(SelectOrderExportActivity context, ArrayList<OrderExport> orderExports) {
        this.context = context;
        this.orderExports = orderExports;
    }

    @NonNull
    @Override
    public OrdersExportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_import, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersExportAdapter.ViewHolder holder, int position) {
        OrderExport orderExport = orderExports.get(position);
        holder.tvOrderId.setText(context.getText(R.string.tv_oderId).toString().concat(String.valueOf(orderExport.getId())));
        holder.tvSupplierName.setText(orderExport.getCustomer().getName());
        holder.tvOrderDate.setText(orderExport.getOderDate());
        holder.tvEmployeeName.setText(orderExport.getEmployee().getFullName()
                .concat(" #")
                .concat(String.valueOf(orderExport.getEmployee().getId()))
        );
//        holder.cardOrder.setOnClickListener(view -> {
//            context.openCreateDeliveryNoteActivity(orderExport);
//        });
    }

    @Override
    public int getItemCount() {
        return orderExports.size();
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
