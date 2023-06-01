package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.fragment.AccountFragment;
import com.example.warehousemanagementwkeeper.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public HomeFragment homeFragment;
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
                case R.id.menuHome:
                    if (homeFragment == null){
                        homeFragment = new HomeFragment();
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, homeFragment)
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
        bottomNavigationView.setSelectedItemId(R.id.menuHome);
    }
}