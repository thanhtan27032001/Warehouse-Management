package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.SelectOrderReceiptActivity;
import com.example.warehousemanagementwkeeper.model.Order;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private SelectOrderReceiptActivity context;
    private ArrayList<Order> orders;

    public OrdersAdapter(SelectOrderReceiptActivity context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvOrderId.setText(context.getText(R.string.tv_oderId).toString().concat(String.valueOf(order.getId())));
        holder.tvSupplierName.setText(" ".concat(order.getSupplier().getName()));
        holder.tvOrderDate.setText(context.getText(R.string.tv_order_date).toString()
                .concat(" ")
                .concat(order.getOderDate())
        );
        holder.tvEmployeeName.setText(context.getText(R.string.tv_created_by).toString()
                .concat(" ")
                .concat(order.getEmployee().getFullName())
                .concat(" #")
                .concat(String.valueOf(order.getEmployee().getId()))
        );
        holder.cardOrder.setOnClickListener(view -> {
//            context.showDialogCreateReceipt(order);
            context.openCreateReceiptActivity(order);
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
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
