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
import com.example.warehousemanagementwkeeper.activity.ViewAllItemsActivity;

public class ItemFragment extends Fragment {
    private LinearLayout cardPutItemToShelf, cardViewAllItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        cardPutItemToShelf = view.findViewById(R.id.cardPutItemToShelf);
        cardViewAllItem = view.findViewById(R.id.cardViewAllItem);

        setEvent();

        return view;
    }

    private void setEvent() {
        cardPutItemToShelf.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), PutItemToShelfActivity.class));
        });
        cardViewAllItem.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ViewAllItemsActivity.class));
        });
    }
}