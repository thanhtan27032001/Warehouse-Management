package com.example.warehousemanagementwkeeper.model;

import com.google.gson.annotations.SerializedName;

public class Employee {
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
    @SerializedName("isActive")
    private boolean isActive;
    @SerializedName("MaBoPhan")
    private int idDepartment;

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
}
