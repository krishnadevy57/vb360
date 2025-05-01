package com.mind2web.vb360.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {
    public static void checkAndRequestPermissions(Activity activity, String[] permissions, Runnable onPermissionsGranted) {
        boolean allGranted = true;

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (allGranted) {
            onPermissionsGranted.run();
        } else {
            ActivityCompat.requestPermissions(activity, permissions, 101);
        }
    }
}
