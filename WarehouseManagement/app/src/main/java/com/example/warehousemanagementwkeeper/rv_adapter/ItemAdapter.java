package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.ViewAllItemsActivity;
import com.example.warehousemanagementwkeeper.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ViewAllItemsActivity context;
    private ArrayList<Item> items;

    public ItemAdapter(ViewAllItemsActivity context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getName());
        holder.itemId.setText(item.getItemId());
        holder.itemQuantity.setText(String.valueOf(item.getInStock()));

        holder.itemLayout.setOnClickListener(view -> {
            context.viewItemDetail(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemLayout;
        private TextView itemName, itemId, itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.rvItem);
            itemName = itemView.findViewById(R.id.tvItemName);
            itemId = itemView.findViewById(R.id.tvItemId);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
