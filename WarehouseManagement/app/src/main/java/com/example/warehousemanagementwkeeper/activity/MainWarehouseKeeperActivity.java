package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.fragment.AccountFragment;
import com.example.warehousemanagementwkeeper.fragment.ExportFragment;
import com.example.warehousemanagementwkeeper.fragment.ImportFragment;
import com.example.warehousemanagementwkeeper.fragment.ItemFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainWarehouseKeeperActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ImportFragment importFragment;
    private ExportFragment exportFragment;
    private ItemFragment itemFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        setEvent();
    }
    private void setView() {
        // Setup bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
    }
    private void setEvent() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menuImport:
                    if (importFragment == null){
                        importFragment = new ImportFragment();
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, importFragment)
                            .commit();
                    break;
                case R.id.menuExport:
                    if (exportFragment == null){
                        exportFragment = new ExportFragment();
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, exportFragment)
                            .commit();
                    break;
                case R.id.menuItem:
                    if (itemFragment == null){
                        itemFragment = new ItemFragment();
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, itemFragment)
                            .commit();
                    break;
                case R.id.menuAccount:
//                    AccountFragment accountFragment = (AccountFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_ACCOUNT);
                    if (accountFragment == null){
                        accountFragment = new AccountFragment();
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, accountFragment)
                            .commit();
                    break;
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menuImport);
    }
}