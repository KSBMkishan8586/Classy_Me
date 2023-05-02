package com.ksbm.ontu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PermissionsUtils {
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    public static void RequestPermissions(Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }



    public static boolean CheckPermissions_record(Activity context) {
        int result = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
}
