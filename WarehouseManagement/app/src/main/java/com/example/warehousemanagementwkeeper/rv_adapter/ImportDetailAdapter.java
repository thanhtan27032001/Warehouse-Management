package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.fragment.ImportDetailFragment;
import com.example.warehousemanagementwkeeper.model.ImportDetail;

import java.util.ArrayList;

public class ImportDetailAdapter extends RecyclerView.Adapter<ImportDetailAdapter.ViewHolder> {

    private ImportDetailFragment context;
    private ArrayList<ImportDetail> importDetails;

    public ImportDetailAdapter(ImportDetailFragment context, ArrayList<ImportDetail> importDetails) {
        this.context = context;
        this.importDetails = importDetails;
    }

    @NonNull
    @Override
    public ImportDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_import_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImportDetailAdapter.ViewHolder holder, int position) {
        ImportDetail importDetail = importDetails.get(position);
        if (position % 2 == 0){
            holder.layoutItem.setBackgroundColor(context.getActivity().getColor(R.color.white));
            holder.btnAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
            holder.btnMinus.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
        }
        else {
            holder.layoutItem.setBackgroundColor(context.getActivity().getColor(R.color.gray_200));
            holder.btnAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
            holder.btnMinus.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
        }
        holder.tvName.setText(importDetail.getItem().getName());
        holder.tvId.setText(String.valueOf(importDetail.getItem().getItemId()));
        holder.edtQuantity.setText(String.valueOf(importDetail.getQuantity()));
        holder.tvOrderQuantity.setText(String.valueOf(importDetail.getQuantityOrder()));
        holder.tvPrice.setText(String.valueOf(importDetail.getPrice()));

        // event
        holder.btnAdd.setOnClickListener(view -> {

        });
        holder.btnMinus.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return importDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutItem;
        private TextView tvName, tvId, tvOrderQuantity, tvPrice;
        private EditText edtQuantity;
        private ImageView btnAdd, btnMinus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvId = itemView.findViewById(R.id.tvItemId);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            edtQuantity = itemView.findViewById(R.id.edtItemQuantity);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
