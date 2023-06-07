package com.example.warehousemanagementwkeeper.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.ImportManagementActivity;
import com.example.warehousemanagementwkeeper.activity.SelectOrderReceiptActivity;

public class HomeFragment extends Fragment {

    private LinearLayout cardCreateReceipt;
    private LinearLayout cardImportManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardCreateReceipt = view.findViewById(R.id.cardCreateReceipt);
        cardImportManagement = view.findViewById(R.id.cardImportManagement);

        setEvent();

        return view;
    }

    private void setEvent() {
        cardCreateReceipt.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), SelectOrderReceiptActivity.class));
        });
        cardImportManagement.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ImportManagementActivity.class));
        });
    }
}