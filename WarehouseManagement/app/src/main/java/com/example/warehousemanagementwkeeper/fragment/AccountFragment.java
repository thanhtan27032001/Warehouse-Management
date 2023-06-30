package com.example.warehousemanagementwkeeper.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.activity.LoginActivity;
import com.example.warehousemanagementwkeeper.model.Employee;
import com.example.warehousemanagementwkeeper.my_control.AuthorizationSingleton;

public class AccountFragment extends Fragment {

    private TextView tvLogo, tvUserName, tvRole, tvPhone, tvAddress;
    private TextView btnChangePassword, btnLogOut;
    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvLogo = view.findViewById(R.id.tvLogo);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvRole = view.findViewById(R.id.tvRole);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvAddress = view.findViewById(R.id.tvAddress);
        btnChangePassword = view.findViewById(R.id.btnChangePasword);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        setEvent();
        setData();

        return view;
    }

    private void setEvent() {
        btnChangePassword.setOnClickListener(view -> {

        });
        btnLogOut.setOnClickListener(view -> {
            logOut();
        });
    }

    private void logOut() {
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .edit()
                .remove(LoginActivity.TAG_USERNAME)
                .remove(LoginActivity.TAG_PASSWORD)
                .remove(LoginActivity.TAG_REMEMBER)
                .apply();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    private void setData() {
        Employee loginEmployee = AuthorizationSingleton.getInstance().getEmployeeInfo();
        tvLogo.setText(loginEmployee.getLastName().substring(0, 1));
        tvUserName.setText(loginEmployee.getFullName());
        tvRole.setText(loginEmployee.getRoleName(getContext()));
        tvPhone.setText(loginEmployee.getPhone());
        tvAddress.setText(loginEmployee.getAddress());
    }
}