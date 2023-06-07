package com.example.warehousemanagementwkeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagementwkeeper.R;
import com.example.warehousemanagementwkeeper.api_instance.AuthorizationApiInstance;
import com.example.warehousemanagementwkeeper.model.Employee;
import com.example.warehousemanagementwkeeper.model.LoginInfo;
import com.example.warehousemanagementwkeeper.model.ResponseEmployee;
import com.example.warehousemanagementwkeeper.model.ResponseLogin;
import com.example.warehousemanagementwkeeper.my_control.MyAuthorization;
import com.example.warehousemanagementwkeeper.my_control.MyFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static int REQUEST_REGISTER_CODE = 2703;
    public static final String TAG_REMEMBER = "TAG_REMEMBER";
    public static final String TAG_USERNAME = "TAG_USERNAME";
    public static final String TAG_PASSWORD = "TAG_PASSWORD";
    private SharedPreferences sharedPreferences;
    private EditText edtUsername, edtPassword;
    private ImageView imgShowPassword;
    private Button btnLogin;
    private CheckBox cbRemember;
    private LinearLayout layoutProgressLogin;
    private TextView tvForgotPassword;

    private boolean isPasswordShowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setControl();
        setEvent();
    }
    private void setEvent() {
        imgShowPassword.setOnClickListener(view -> {
            isPasswordShowed = !isPasswordShowed;
            int selectionIndex = edtPassword.getSelectionEnd();
            if (isPasswordShowed){
                imgShowPassword.setImageResource(R.drawable.baseline_visibility_24);
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else {
                imgShowPassword.setImageResource(R.drawable.baseline_visibility_off_24);
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            edtPassword.setSelection(selectionIndex);
        });
        btnLogin.setOnClickListener(view -> {
            if (isValidated()){
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                LoginInfo loginInfo = new LoginInfo(username, password);
                login(loginInfo);
            }
        });
        tvForgotPassword.setOnClickListener(view -> {
//            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
    }

    private void setControl() {
        edtUsername = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        imgShowPassword = findViewById(R.id.imgShowPassword);
        btnLogin = findViewById(R.id.btnLogin);
        layoutProgressLogin = findViewById(R.id.layoutProgressLogin);
        cbRemember = findViewById(R.id.cbRemember);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        setData();
    }

    private void setData() {
        boolean remember = sharedPreferences.getBoolean(TAG_REMEMBER, false);
        if (remember){
            String username = sharedPreferences.getString(TAG_USERNAME, "");
            String password = sharedPreferences.getString(TAG_PASSWORD, "");
            if (!username.equals("") && !password.equals("")){
                cbRemember.setChecked(true);
                edtUsername.setText(username);
                edtPassword.setText(password);
                LoginInfo loginInfo = new LoginInfo(username, password);
                login(loginInfo);
            }
        }
    }

    private void rememberLogin(LoginInfo loginInfo){
        sharedPreferences
                .edit()
                .putBoolean(TAG_REMEMBER, true)
                .putString(TAG_USERNAME, loginInfo.getUsername())
                .putString(TAG_PASSWORD, loginInfo.getPassword())
                .apply();
    }

    private boolean isValidated() {
        if (edtUsername.getText().toString().equals("")){
            edtUsername.setError(getText(R.string.error_miss_username));
            edtUsername.requestFocus();
            return false;
        }
        if (edtPassword.getText().toString().equals("")){
            edtPassword.setError(getText(R.string.error_miss_password));
            edtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void login(LoginInfo loginInfo){
        layoutProgressLogin.setVisibility(View.VISIBLE);
        Call<ResponseLogin> callLogin = AuthorizationApiInstance.getInstance().login(loginInfo);
        callLogin.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    // get employee info
                    String token = response.body().getData();
                    Call<ResponseEmployee> callEmployee = AuthorizationApiInstance.getInstance().getEmployeeInfo("Bearer " + token);
                    callEmployee.enqueue(new Callback<ResponseEmployee>() {
                        @Override
                        public void onResponse(Call<ResponseEmployee> call, Response<ResponseEmployee> response) {
                            if (response.isSuccessful()){
                                Employee employee = response.body().getData();
                                MyAuthorization.getInstance().setLoginInfo(loginInfo);
                                MyAuthorization.getInstance().setEmployeeInfo(employee);
                                MyAuthorization.getInstance().setToken(token);
                                // open main screen
                                Intent intent = MainActivityFactory.getActivityIntent(LoginActivity.this, employee.getRole());
                                if (intent != null){
                                    rememberLogin(loginInfo);
                                    startActivity(intent);
                                    switch (employee.getRole()){
                                        case Employee.ROLE_EMPLOYEE:
                                            Toast.makeText(LoginActivity.this, R.string.role_employee, Toast.LENGTH_SHORT).show();
                                            break;
                                        case Employee.ROLE_WAREHOUSE_KEEPER:
                                            Toast.makeText(LoginActivity.this, R.string.role_warehouse_keeper, Toast.LENGTH_SHORT).show();
                                            break;
                                        case Employee.ROLE_ACCOUNTANT:
                                            Toast.makeText(LoginActivity.this, R.string.option_accountant, Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("error", response.errorBody().string());
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseEmployee> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("error", response.errorBody().string());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                layoutProgressLogin.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.error_500, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                layoutProgressLogin.setVisibility(View.GONE);
            }
        });
    }
}