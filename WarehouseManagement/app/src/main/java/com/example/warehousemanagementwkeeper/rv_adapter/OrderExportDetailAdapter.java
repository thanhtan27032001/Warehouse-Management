package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.CreateDeliveryNoteActivity;
import com.example.warehousemanagementwkeeper.model.OrderExportDetail;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;

import java.util.ArrayList;

public class OrderExportDetailAdapter extends RecyclerView.Adapter<OrderExportDetailAdapter.ViewHolder> {

    private CreateDeliveryNoteActivity context;
    private ArrayList<OrderExportDetail> orderImportDetails;

    public OrderExportDetailAdapter(CreateDeliveryNoteActivity context, ArrayList<OrderExportDetail> orderImportDetails) {
        this.context = context;
        this.orderImportDetails = orderImportDetails;
    }

    @NonNull
    @Override
    public OrderExportDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_export_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderExportDetailAdapter.ViewHolder holder, int position) {
        OrderExportDetail orderImportDetail = orderImportDetails.get(position);
        holder.layoutItem.setBackgroundColor(position % 2 == 0 ? context.getColor(R.color.white) : context.getColor(R.color.gray_200));
        holder.tvName.setText(orderImportDetail.getItem().getName());
        holder.tvId.setText(String.valueOf(orderImportDetail.getItem().getItemId()));
        holder.tvQuantity.setText(String.valueOf(orderImportDetail.getQuantity()));
        holder.tvInStock.setText(String.valueOf(orderImportDetail.getItem().getInStock()));
        holder.tvPrice.setText(StringFormatFacade.getStringPrice(orderImportDetail.getPrice()));
    }

    @Override
    public int getItemCount() {
        return orderImportDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutItem;
        private TextView tvName, tvId, tvQuantity, tvInStock, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvId = itemView.findViewById(R.id.tvItemId);
            tvQuantity = itemView.findViewById(R.id.tvItemQuantity);
            tvInStock = itemView.findViewById(R.id.tvItemInStock);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
        }
    }
}
