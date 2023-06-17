package com.example.warehousemanagementwkeeper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.ReceiptApiInstance;
import com.example.warehousemanagementwkeeper.fragment.ImportDetailFragment;
import com.example.warehousemanagementwkeeper.fragment.ReceiptFragment;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.my_control.AuthorizationSingleton;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public static final int REQUEST_RECEIPT_IS_UPDATED = 1111;
    public static final String TAG_RECEIPT_SELECTED = "TAG_RECEIPT_SELECTED";
    private Receipt receiptSelected;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabLayoutMediator tabLayoutMediator;
    private FragmentStateAdapter pagerAdapter;
    private ImageButton btnBack, btnMore;

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
        btnMore = findViewById(R.id.btnMore);
    }

    private void setEvent() {
        btnBack.setOnClickListener(view -> finish());
        btnMore.setOnClickListener(view -> showMoreOptionReceiptDetail());
    }

    private void setData() {
        receiptSelected = (Receipt) getIntent().getSerializableExtra(TAG_RECEIPT_SELECTED);
        // set btn finish
        if (receiptSelected.getStatus() == DeliveryNote.STATUS_DONE){
            btnMore.setVisibility(View.GONE);
        }
        else {
            btnMore.setVisibility(View.VISIBLE);
        }
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

    private void showMoreOptionReceiptDetail() {
        PopupMenu menu = new PopupMenu(this, btnMore);
        menu.getMenuInflater().inflate(R.menu.menu_receipt_detail, menu.getMenu());

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.optionFinish:
                        finishImporting();
                        break;
                    case R.id.optionDelete:
                        deleteReceipt();
                        break;
                }
                return false;
            }
        });

        menu.show();
    }

    private void deleteReceipt() {
        Call<ResponseObject> call = ReceiptApiInstance.getInstance().deleteReceipt(
                AuthorizationSingleton.getInstance().getBearerToken(),
                receiptSelected.getReceiptId());
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ReceiptDetailActivity.this, R.string.success_delete_receipt, Toast.LENGTH_SHORT).show();
                    ReceiptDetailActivity.this.setResult(RESULT_OK);
                    ReceiptDetailActivity.this.finish();
                }
                else {
                    try {
                        Toast.makeText(ReceiptDetailActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ReceiptDetailActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finishImporting() {
        receiptSelected.setStatus(true);
        Call<ResponseObject> call = ReceiptApiInstance.getInstance().updateReceiptStatus(
                AuthorizationSingleton.getInstance().getBearerToken(), 
                receiptSelected.getReceiptId(),
                receiptSelected);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ReceiptDetailActivity.this, R.string.success_finish_importing, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                }
                else {
                    receiptSelected.setStatus(false);
                    try {
                        Toast.makeText(ReceiptDetailActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                receiptSelected.setStatus(false);
                Toast.makeText(ReceiptDetailActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}