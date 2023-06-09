package com.example.warehousemanagementwkeeper.my_control;

import com.example.warehousemanagementwkeeper.model.Employee;
import com.example.warehousemanagementwkeeper.model.LoginInfo;

public class AuthorizationSingleton {
    private static AuthorizationSingleton instance;
    private Employee employeeInfo;
    private LoginInfo loginInfo;
    private String token;
    public static AuthorizationSingleton getInstance(){
        if (instance == null){
            instance = new AuthorizationSingleton();
        }
        return instance;
    }

    public void setEmployeeInfo(Employee employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
    public void setToken(String token){
        this.token = token;
    }

    public Employee getEmployeeInfo() {
        return employeeInfo;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public String getBearerToken() {
        return "Bearer " + token;
    }
}
