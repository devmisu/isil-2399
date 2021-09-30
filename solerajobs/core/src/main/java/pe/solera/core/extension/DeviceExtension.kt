package pe.solera.core.extension

import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager

fun Context?.getDensity(): Float {
    return this?.let {
        val metrics = DisplayMetrics().apply {
            (it.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(this)
        }
        return metrics.density
    } ?: 0f
}

fun Context?.getWidth() : Int {
    return this?.let {
        val size = Point().apply {
            val windowsManager = it.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowsManager.defaultDisplay.getSize(this)
        }
        return size.x
    } ?: 0
}

fun Context?.getHeight() : Int {
    return this?.let {
        val size = Point().apply {
            val windowsManager = it.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowsManager.defaultDisplay.getSize(this)
        }
        return size.y
    } ?: 0
}

fun Context?.isAirplaneModeActive() : Boolean {
    return this?.let {
        return Settings.Global.getInt(it.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } ?: false
}

fun Context?.isConnected() : Boolean {
    return this?.let {
        val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.getNetworkCapabilities(cm.activeNetwork)?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
    } ?: false
}

fun Context?.hasMobileData() : Boolean {
    return this?.let {
        val p = Runtime.getRuntime().exec("ping -c 1 www.google.com")
        val test = p.waitFor()
        return test == 0
    } ?: false
}