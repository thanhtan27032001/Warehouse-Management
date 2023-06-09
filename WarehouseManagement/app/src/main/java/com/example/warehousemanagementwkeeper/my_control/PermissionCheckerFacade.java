package com.example.warehousemanagementwkeeper.my_control;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.Activity;

import androidx.core.content.PermissionChecker;

public class PermissionCheckerFacade {
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_CODE_CAMERA_PERMISSION = 2;

    public static boolean checkReadFilePermission(Activity context){
        return checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED;
    }

    public static boolean checkReadFilePermissionApi33(Activity context){
        return checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PermissionChecker.PERMISSION_GRANTED;
    }

    public static boolean checkCameraPermission(Activity context){
        return checkSelfPermission(context, Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED;
    }

    public static boolean checkFineLocationPermission(Activity context){
        return checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED;
    }
}
