package me.lucky.catcher

import android.Manifest
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class Utils {
    companion object {
        fun hasInternet(ctx: Context, packageName: String): Boolean {
            val info: PackageInfo
            try {
                info = ctx
                    .packageManager
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            } catch (exc: PackageManager.NameNotFoundException) { return false }
            return info.requestedPermissions?.contains(Manifest.permission.INTERNET) ?: false
        }
    }
}