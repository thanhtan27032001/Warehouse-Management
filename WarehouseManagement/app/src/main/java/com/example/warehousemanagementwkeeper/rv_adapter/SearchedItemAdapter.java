package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.CheckItemStockActivity;
import com.example.warehousemanagementwkeeper.model.Item;

import java.util.ArrayList;

public class SearchedItemAdapter extends RecyclerView.Adapter<SearchedItemAdapter.ViewHolder> {
    private CheckItemStockActivity context;
    private ArrayList<Item> items;

    public SearchedItemAdapter(CheckItemStockActivity context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SearchedItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_searched, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedItemAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.tvItemName.setText(item.getName());
        holder.tvItemId.setText(item.getItemId());
        holder.layout.setOnClickListener(view -> {
            context.viewItemLocation(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private TextView tvItemName, tvItemId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemId = itemView.findViewById(R.id.tvItemId);
        }
    }
}
