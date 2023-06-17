package com.example.warehousemanagementwkeeper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.DeliveryNoteApiInstance;
import com.example.warehousemanagementwkeeper.fragment.DeliveryNoteFragment;
import com.example.warehousemanagementwkeeper.fragment.ExportDetailFragment;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.ResponseObject;
import com.example.warehousemanagementwkeeper.my_control.AuthorizationSingleton;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryNoteDetailActivity extends AppCompatActivity {

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new DeliveryNoteFragment(deliveryNote);
                case 1:
                    return new ExportDetailFragment(deliveryNote);
            }
            return new DeliveryNoteFragment(deliveryNote);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
    public static final int REQUEST_DELIVERY_NOTE_IS_UPDATED = 1111;
    public static final String TAG_DELIVERY_NOTE_SELECTED = "TAG_RECEIPT_SELECTED";
    private DeliveryNote deliveryNote;
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
        deliveryNote = (DeliveryNote) getIntent().getSerializableExtra(TAG_DELIVERY_NOTE_SELECTED);

        // set btn finish
        if (deliveryNote.getStatus() == DeliveryNote.STATUS_DONE){
            btnMore.setVisibility(View.GONE);
        }
        else {
            btnMore.setVisibility(View.VISIBLE);
        }
        // set fragment
        pagerAdapter = new DeliveryNoteDetailActivity.ScreenSlidePagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(getText(R.string.tv_delivery_note));
                        break;
                    case 1:
                        tab.setText(R.string.tab_export_detail);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private void showMoreOptionReceiptDetail() {
        PopupMenu menu = new PopupMenu(this, btnMore);
        menu.getMenuInflater().inflate(R.menu.menu_delivery_note_detail, menu.getMenu());

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.optionFinish:
                        finishExporting();
                        break;
                    case R.id.optionDelete:
                        showDialogDeleteDeliveryNote();
                        break;
                }
                return false;
            }
        });

        menu.show();
    }

    private void showDialogDeleteDeliveryNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(R.string.title_delete_delivery_note)
                .setMessage(R.string.message_delete_delivery_note)
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.btn_submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDeliveryNote();
                    }
                })
                .create()
                .show();
    }

    private void deleteDeliveryNote() {
        Call<ResponseObject> call = DeliveryNoteApiInstance.getInstance().deleteDeliveryNote(
                AuthorizationSingleton.getInstance().getBearerToken(),
                deliveryNote.getDeliveryNoteId());
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DeliveryNoteDetailActivity.this, R.string.success_delete_delivery_note, Toast.LENGTH_SHORT).show();
                    DeliveryNoteDetailActivity.this.setResult(RESULT_OK);
                    DeliveryNoteDetailActivity.this.finish();
                }
                else {
                    try {
                        Log.e("error", response.errorBody().string());
//                        Toast.makeText(DeliveryNoteDetailActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(DeliveryNoteDetailActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void finishExporting() {
        deliveryNote.setStatus(true);
        Call<ResponseObject> call = DeliveryNoteApiInstance.getInstance().updateDeliveryNoteStatus(
                AuthorizationSingleton.getInstance().getBearerToken(),
                deliveryNote.getDeliveryNoteId(),
                deliveryNote);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DeliveryNoteDetailActivity.this, R.string.success_finish_exporting, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                }
                else {
                    deliveryNote.setStatus(false);
                    try {
//                        Toast.makeText(DeliveryNoteDetailActivity.this, StringFormatFacade.getError(response.errorBody().string()), Toast.LENGTH_SHORT).show();
                        Log.e("error", response.errorBody().string());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                deliveryNote.setStatus(false);
                Toast.makeText(DeliveryNoteDetailActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}