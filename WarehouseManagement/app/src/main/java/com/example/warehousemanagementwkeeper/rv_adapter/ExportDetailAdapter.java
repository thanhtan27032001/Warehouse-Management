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
import com.example.warehousemanagementwkeeper.fragment.ExportDetailFragment;
import com.example.warehousemanagementwkeeper.fragment.ImportDetailFragment;
import com.example.warehousemanagementwkeeper.model.ExportDetail;
import com.example.warehousemanagementwkeeper.model.ImportDetail;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;

import java.util.ArrayList;

public class ExportDetailAdapter extends RecyclerView.Adapter<ExportDetailAdapter.ViewHolder> {

    private ExportDetailFragment context;
    private ArrayList<ExportDetail> importDetails;

    public ExportDetailAdapter(ExportDetailFragment context, ArrayList<ExportDetail> importDetails) {
        this.context = context;
        this.importDetails = importDetails;
    }

    @NonNull
    @Override
    public ExportDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_import_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExportDetailAdapter.ViewHolder holder, int position) {
        ExportDetail exportDetail = importDetails.get(position);
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
        holder.tvName.setText(exportDetail.getItem().getName());
        holder.tvId.setText(String.valueOf(exportDetail.getItem().getItemId()));
        holder.edtQuantity.setText(String.valueOf(exportDetail.getQuantity()));
        holder.tvOrderQuantity.setText(String.valueOf(exportDetail.getQuantityOrder()));
        holder.tvPrice.setText(StringFormatFacade.getStringPrice(exportDetail.getPrice()));

        // event
        holder.btnAdd.setOnClickListener(view -> {
            if (exportDetail.getQuantity()+1 <= exportDetail.getQuantityOrder()){
                context.upsertExportDetail(exportDetail, exportDetail.getQuantity()+1, exportDetail.getPrice(), null);
            }
            else {
                Toast.makeText(context.getContext(), R.string.warning_maximum_quantity, Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnMinus.setOnClickListener(view -> {
            if (exportDetail.getQuantity()-1 >= 0){
                context.upsertExportDetail(exportDetail, exportDetail.getQuantity()-1, exportDetail.getPrice(), null);
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
                else if (Integer.parseInt(holder.edtQuantity.getText().toString()) > exportDetail.getQuantityOrder()){
                    holder.edtQuantity.setText(String.valueOf(exportDetail.getQuantityOrder()));
                }
                context.upsertExportDetail(exportDetail, Integer.parseInt(holder.edtQuantity.getText().toString()), Long.parseLong(holder.tvPrice.getText().toString()), null);
                return false;
            }
        });
        holder.layoutItem.setOnClickListener(view -> {
            context.showDialogExportDetail(exportDetail);
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
