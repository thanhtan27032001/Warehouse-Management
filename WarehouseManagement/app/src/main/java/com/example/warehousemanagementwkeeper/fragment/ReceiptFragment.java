package com.example.warehousemanagementwkeeper.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.model.Receipt;
import com.example.warehousemanagementwkeeper.my_control.StringFormatFacade;

public class ReceiptFragment extends Fragment {
    private Receipt receipt;
    private TextView tvStatus, tvInputDate, tvInputTime, tvReceiptEmployeeName, tvOrderDate, tvOrderEmployeeName,
        tvSupplier, tvSupplierAddress, tvSupplierPhone;
    public ReceiptFragment(Receipt receipt) {
        this.receipt = receipt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvInputDate = view.findViewById(R.id.tvInputDate);
        tvInputTime = view.findViewById(R.id.tvInputTime);
        tvReceiptEmployeeName = view.findViewById(R.id.tvReceiptEmployeeCreate);
        tvOrderDate = view.findViewById(R.id.tvOrderDate);
        tvOrderEmployeeName = view.findViewById(R.id.tvOrderEmployeeCreate);
        tvSupplier = view.findViewById(R.id.tvSupplierName);
        tvSupplierAddress = view.findViewById(R.id.tvSupplierAddress);
        tvSupplierPhone = view.findViewById(R.id.tvSupplierPhone);

        setEvent();
        setData();

        return view;
    }

    private void setEvent() {

    }

    private void setData() {
        tvStatus.setText(receipt.getStatus() == Receipt.STATUS_DONE ? R.string.status_done : R.string.status_importing);
        tvInputDate.setText(StringFormatFacade.getDateOnly(receipt.getInputDateTime()));
        tvInputTime.setText(StringFormatFacade.getTimeOnly(receipt.getInputDateTime()));
        tvReceiptEmployeeName.setText(receipt.getEmployee().getFullName());
        tvOrderDate.setText(receipt.getOrder().getOderDate());
//        tvOrderEmployeeName.setText(receipt.getOrder().getEmployee().getFullName());
        tvSupplier.setText(receipt.getOrder().getSupplier().getName());
        tvSupplierAddress.setText(receipt.getOrder().getSupplier().getAddress());
        tvSupplierPhone.setText(receipt.getOrder().getSupplier().getPhone());
    }
}