package pe.solera.core.extension


import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pe.solera.core.BuildConfig
import pe.solera.core.ConstantsCore
import pe.solera.core.R

fun Context.showMaterialDialog(
    title: String? = null,
    message: String? = null,
    acceptBtnMsg: String? = null,
    showNegativeBtn: Boolean = false,
    action: ((action: Boolean) -> Unit)? = null
) : AlertDialog {
    return MaterialAlertDialogBuilder(this).let { builder ->
        builder.setCancelable(false)
        builder.setTitle(title ?: BuildConfig.APP_NAME)
        builder.setMessage(message ?: ConstantsCore.empty)
        builder.setPositiveButton(acceptBtnMsg ?: this.getString(R.string.accept)) { dialog, _ ->
            dialog.dismiss()
            action?.invoke(true)
        }
        if (showNegativeBtn) { builder.setNegativeButton(this.getText(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
            action?.invoke(false)
        } }
        builder.show()
    }
}