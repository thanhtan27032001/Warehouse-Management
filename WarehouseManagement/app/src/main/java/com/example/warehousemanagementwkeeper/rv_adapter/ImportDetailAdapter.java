package com.example.warehousemanagementwkeeper.rv_adapter;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.fragment.ImportDetailFragment;
import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;

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
            holder.layoutItem.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
            holder.btnAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
            holder.btnMinus.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
        }
        else {
            holder.layoutItem.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
            holder.btnAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
            holder.btnMinus.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray_200));
        }
        holder.tvName.setText(importDetail.getItem().getName());
        holder.tvId.setText(String.valueOf(importDetail.getItem().getItemId()));
        holder.edtQuantity.setText(String.valueOf(importDetail.getQuantity()));
        holder.tvOrderQuantity.setText(String.valueOf(importDetail.getQuantityOrder()));
        holder.tvPrice.setText(StringFormatFacade.getStringPrice(importDetail.getPrice()));

        // event
        holder.btnAdd.setOnClickListener(view -> {
            if (importDetail.getQuantity()+1 <= importDetail.getQuantityOrder()){
                context.upsertImportDetail(importDetail, importDetail.getQuantity()+1, Long.parseLong(holder.tvPrice.getText().toString()), null);
            }
            else {
                Toast.makeText(context.getContext(), R.string.warning_maximum_quantity, Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnMinus.setOnClickListener(view -> {
            if (importDetail.getQuantity()-1 >= 0){
                context.upsertImportDetail(importDetail, importDetail.getQuantity()-1, Long.parseLong(holder.tvPrice.getText().toString()), null);
            }
            else {
                Toast.makeText(context.getContext(), R.string.warning_minimum_quantity, Toast.LENGTH_SHORT).show();
            }
        });
        holder.edtQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Toast.makeText(context.getContext(), "Done", Toast.LENGTH_SHORT).show();
                if (holder.edtQuantity.getText().toString().trim().equals("")){
                    holder.edtQuantity.setText("0");
                }
                else if (Integer.parseInt(holder.edtQuantity.getText().toString()) > importDetail.getQuantityOrder()){
                    holder.edtQuantity.setText(String.valueOf(importDetail.getQuantityOrder()));
                }
                context.upsertImportDetail(importDetail, Integer.parseInt(holder.edtQuantity.getText().toString()), Long.parseLong(holder.tvPrice.getText().toString()), null);
                return false;
            }
        });
        holder.layoutItem.setOnClickListener(view -> {
            context.showDialogImportDetail(importDetail);
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
