package com.example.warehousemanagementwkeeper.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.model.DeliveryNote;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;

public class DeliveryNoteFragment extends Fragment {

    private DeliveryNote receipt;
    private TextView tvStatus, tvInputDate, tvInputTime, tvReceiptEmployeeName, tvOrderDate, tvOrderEmployeeName,
            tvSupplier, tvOrderAddress, tvSupplierPhone;
    public DeliveryNoteFragment(DeliveryNote receipt) {
        this.receipt = receipt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_note, container, false);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvInputDate = view.findViewById(R.id.tvInputDate);
        tvInputTime = view.findViewById(R.id.tvInputTime);
        tvReceiptEmployeeName = view.findViewById(R.id.tvReceiptEmployeeCreate);
        tvOrderDate = view.findViewById(R.id.tvOrderDate);
        tvOrderEmployeeName = view.findViewById(R.id.tvOrderEmployeeCreate);
//        tvSupplier = view.findViewById(R.id.tvSupplierName);
        tvOrderAddress = view.findViewById(R.id.tvOrderAddress);
        tvSupplierPhone = view.findViewById(R.id.tvOrderPhone);

        setEvent();
        setData();

        return view;
    }

    private void setEvent() {

    }

    private void setData() {
        tvStatus.setText(receipt.getStatus() == Receipt.STATUS_DONE ? R.string.status_done : R.string.status_importing);
        tvInputDate.setText(StringFormatFacade.getDateOnly(receipt.getExportDateTime()));
        tvInputTime.setText(StringFormatFacade.getTimeOnly(receipt.getExportDateTime()));
        tvReceiptEmployeeName.setText(receipt.getEmployee().getFullName());
        tvOrderDate.setText(receipt.getOrder().getOderDate());
//        tvOrderEmployeeName.setText(receipt.getOrder().getEmployee().getFullName());
//        tvSupplier.setText(receipt.getOrder().getCustomer().getName());
        tvOrderAddress.setText(receipt.getOrder().getCustomer().getAddress());
        tvSupplierPhone.setText(receipt.getOrder().getCustomer().getPhone());
    }
}