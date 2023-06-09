package com.example.warehousemanagementwkeeper.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.CheckItemStockActivity;
import com.example.warehousemanagementwkeeper.activity.ExportManagementActivity;
import com.example.warehousemanagementwkeeper.activity.SelectOrderExportActivity;

public class ExportFragment extends Fragment {

    private LinearLayout cardCreateDeliveryNote, cardExportManagement, cardCheckItemStock, cardCheckItemStockDelivery;

    public ExportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);

        cardCreateDeliveryNote = view.findViewById(R.id.cardCreateDeliveryNote);
        cardCheckItemStock = view.findViewById(R.id.cardCheckItemStock);
        cardExportManagement = view.findViewById(R.id.cardExportManagement);

        setEvent();
        setData();

        return view;
    }

    private void setEvent() {
        cardCreateDeliveryNote.setOnClickListener(view -> startActivity(new Intent(getContext(), SelectOrderExportActivity.class)));
        cardExportManagement.setOnClickListener(view -> startActivity(new Intent(getContext(), ExportManagementActivity.class)));
        cardCheckItemStock.setOnClickListener(view -> startActivity(new Intent(getContext(), CheckItemStockActivity.class)));
    }

    private void setData() {

    }
}