package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.CreateReceiptActivity;
import com.example.warehousemanagementwkeeper.model.OrderDetail;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private CreateReceiptActivity context;
    private ArrayList<OrderDetail> orderDetails;

    public OrderDetailAdapter(CreateReceiptActivity context, ArrayList<OrderDetail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetails.get(position);
        holder.layoutItem.setBackgroundColor(position % 2 == 0 ? context.getColor(R.color.white) : context.getColor(R.color.gray_200));
        holder.tvName.setText(orderDetail.getItemName());
        holder.tvId.setText(String.valueOf(orderDetail.getItemId()));
        holder.tvQuantity.setText(String.valueOf(orderDetail.getQuantity()));
        holder.tvPrice.setText(String.valueOf(orderDetail.getPrice()));
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutItem;
        private TextView tvName, tvId, tvQuantity, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvId = itemView.findViewById(R.id.tvItemId);
            tvQuantity = itemView.findViewById(R.id.tvItemQuantity);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
        }
    }
}
