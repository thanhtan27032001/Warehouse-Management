package com.example.warehousemanagementwkeeper.model;

import android.content.Context;

import com.example.warehousemanagementwkeeper.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {
    public static final String ROLE_EMPLOYEE = "NV";
    public static final String ROLE_WAREHOUSE_KEEPER = "TK";
    public static final String ROLE_MANAGER = "QL";
    public static final String ROLE_ACCOUNTANT = "KT";
    public static final String ROLE_BUSINESS = "KD";
    @SerializedName("MaNhanVien")
    private int id;
    @SerializedName("Ho")
    private String firstName;
    @SerializedName("Ten")
    private String lastName;
    @SerializedName("NgaySinh")
    private String birthdate;
    @SerializedName("DiaChi")
    private String address;
    @SerializedName("SDT")
    private String phone;
    @SerializedName("isActive")
    private boolean isActive;
    @SerializedName("MaBoPhan")
    private int idDepartment;
    @SerializedName("Role")
    private String role;

    // get employee info in order
    public Employee(int id, String firstName, String lastName, String birthdate, String address, boolean isActive, int idDepartment) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.isActive = isActive;
        this.idDepartment = idDepartment;
    }

    // login
    public Employee(int id, String firstName, String lastName, String birthdate, String address, String phone, boolean isActive, int idDepartment, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
        this.idDepartment = idDepartment;
        this.role = role;
    }

    public String getRoleName(Context context){
        switch (role){
            case ROLE_EMPLOYEE:
                return context.getText(R.string.role_employee).toString();
            case ROLE_WAREHOUSE_KEEPER:
                return context.getText(R.string.role_warehouse_keeper).toString();
            case ROLE_ACCOUNTANT:
                return context.getText(R.string.role_accountant).toString();
        }
        return context.getText(R.string.error_role_not_found).toString();
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }
}
