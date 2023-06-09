package com.example.warehousemanagementwkeeper.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.ImportManagementActivity;
import com.example.warehousemanagementwkeeper.activity.PutItemToShelfActivity;
import com.example.warehousemanagementwkeeper.activity.SelectOrderImportActivity;

public class ImportFragment extends Fragment {

    private LinearLayout cardCreateReceipt, cardImportManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);

        cardCreateReceipt = view.findViewById(R.id.cardCreateReceipt);
        cardImportManagement = view.findViewById(R.id.cardImportManagement);

        setEvent();

        return view;
    }

    private void setEvent() {
        cardCreateReceipt.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), SelectOrderImportActivity.class));
        });
        cardImportManagement.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ImportManagementActivity.class));
        });
    }
}