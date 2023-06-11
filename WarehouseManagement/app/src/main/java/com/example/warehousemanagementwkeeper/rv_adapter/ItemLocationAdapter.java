package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.ItemStockActivity;
import com.example.warehousemanagementwkeeper.model.ItemLocation;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;

import java.util.ArrayList;

public class ItemLocationAdapter extends RecyclerView.Adapter<ItemLocationAdapter.ViewHolder> {
    private ItemStockActivity context;
    private ArrayList<ItemLocation> itemLocations;

    public ItemLocationAdapter(ItemStockActivity context, ArrayList<ItemLocation> itemLocations) {
        this.context = context;
        this.itemLocations = itemLocations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemLocation itemLocation = itemLocations.get(position);
        holder.tvItemLocation.setText(StringFormatFacade.getItemLocation(context, itemLocation.getBoxId()));
        holder.tvCurrentQuantity.setText(String.valueOf(itemLocation.getCurrentQuantity()));
        holder.tvInitQuantity.setText(String.valueOf(itemLocation.getInitialQuantity()));
    }

    @Override
    public int getItemCount() {
        return itemLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemLocation, tvInitQuantity, tvCurrentQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemLocation = itemView.findViewById(R.id.tvLocation);
            tvInitQuantity = itemView.findViewById(R.id.tvInitQuantity);
            tvCurrentQuantity = itemView.findViewById(R.id.tvCurrentQuantity);
        }
    }
}
