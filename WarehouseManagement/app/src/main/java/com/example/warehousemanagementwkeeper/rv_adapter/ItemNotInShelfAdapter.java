package com.example.warehousemanagementwkeeper.rv_adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.PutItemToShelfActivity;
import com.example.warehousemanagementwkeeper.model.ItemNotInShelf;

import java.util.ArrayList;

public class ItemNotInShelfAdapter extends RecyclerView.Adapter<ItemNotInShelfAdapter.ViewHolder> {
    private PutItemToShelfActivity context;
    private ArrayList<ItemNotInShelf> itemsNotInShelf;

    public ItemNotInShelfAdapter(PutItemToShelfActivity context, ArrayList<ItemNotInShelf> itemsNotInShelf) {
        this.context = context;
        this.itemsNotInShelf = itemsNotInShelf;
    }

    @NonNull
    @Override
    public ItemNotInShelfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_not_in_shelf, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNotInShelfAdapter.ViewHolder holder, int position) {
        ItemNotInShelf itemNotInShelf = itemsNotInShelf.get(position);
        holder.tvItemName.setText(itemNotInShelf.getItem().getName());
        holder.tvItemId.setText(itemNotInShelf.getItem().getItemId());
        holder.tvItemQuantityNotInShelf.setText(String.valueOf(itemNotInShelf.getQuantity()));
        holder.tvItemQuantity.setText(String.valueOf(itemNotInShelf.getItem().getInStock()));

        holder.rvItem.setBackgroundTintList(ColorStateList.valueOf(
                context.getColor(position % 2 == 0 ? R.color.white : R.color.gray_200)));

        holder.rvItem.setOnClickListener(view -> {
            context.showDialogPutItemToShelf(itemNotInShelf);
        });
    }

    @Override
    public int getItemCount() {
        return itemsNotInShelf.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rvItem;
        private TextView tvItemName, tvItemId, tvItemQuantityNotInShelf, tvItemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvItem = itemView.findViewById(R.id.rvItem);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemId = itemView.findViewById(R.id.tvItemId);
            tvItemQuantityNotInShelf = itemView.findViewById(R.id.itemQuantityNotInShelf);
            tvItemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
