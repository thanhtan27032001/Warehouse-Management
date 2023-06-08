package com.example.warehousemanagementwkeeper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.fragment.ImportDetailFragment;
import com.example.warehousemanagementwkeeper.fragment.ReceiptFragment;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReceiptDetailActivity extends AppCompatActivity {

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new ReceiptFragment(receiptSelected);
                case 1:
                    return new ImportDetailFragment(receiptSelected);
            }
            return new ReceiptFragment(receiptSelected);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
    public static final String TAG_RECEIPT_SELECTED = "TAG_RECEIPT_SELECTED";
    private Receipt receiptSelected;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabLayoutMediator tabLayoutMediator;
    private FragmentStateAdapter pagerAdapter;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detail);

        setView();
        setEvent();
        setData();

    }

    private void setView() {
        viewPager2 = findViewById(R.id.pagerContent);
        tabLayout = findViewById(R.id.tabLayoutContent);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
    }

    private void setData() {
        receiptSelected = (Receipt) getIntent().getSerializableExtra(TAG_RECEIPT_SELECTED);

        // set fragment
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(getText(R.string.tv_receipt));
                        break;
                    case 1:
                        tab.setText(R.string.tab_import_detail);
                }
            }
        });
        tabLayoutMediator.attach();
    }
}