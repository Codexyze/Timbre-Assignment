package com.nutrino.timbreassignment.core.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * Helper utility for determining and checking runtime Android permissions required for media accessing and editing.
 */
object PermissionUtils {

    /**
     * Returns the array of runtime media permissions required based on the device Android API level.
     *
     * On Android 13+ (API level 33, Tiramisu), granular permissions (`READ_MEDIA_AUDIO`, `READ_MEDIA_VIDEO`,
     * `READ_MEDIA_IMAGES`) are requested. On older versions, `READ_EXTERNAL_STORAGE` and `WRITE_EXTERNAL_STORAGE` are returned.
     *
     * @return Array of permission manifest strings.
     */
    fun getRequiredMediaPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    /**
     * Checks if all required media storage permissions have been granted by the user.
     *
     * @param context [Context] used to check runtime permission statuses.
     * @return `true` if all permissions are granted, `false` otherwise.
     */
    fun hasAllMediaPermissions(context: Context): Boolean {
        return getRequiredMediaPermissions().all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}
