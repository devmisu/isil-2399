package pe.solera.core.extension

import android.content.Context
import android.widget.Toast

fun Context?.showToast(message: String) {
    this ?: return
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}