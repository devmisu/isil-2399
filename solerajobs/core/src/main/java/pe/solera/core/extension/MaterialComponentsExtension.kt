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

fun Context.showMaterialDialogWithOptions(
    itemNames: Array<String>,
    title: String = String(),
    titleId: Int? = null,
    showNegativeButton: Boolean = false,
    previousSelectedItemIndex: Int = -1,
    selection: (index:Int, name: String, action: Boolean) -> Unit
) : AlertDialog {

    var selectedItemIndex : Int = previousSelectedItemIndex

    return MaterialAlertDialogBuilder(this).let { builder ->
        builder.setCancelable(false)
        builder.setTitle(if (titleId != null) getText(titleId) else title)
        builder.setSingleChoiceItems(itemNames, selectedItemIndex) { _, checkedItem ->
            selectedItemIndex = checkedItem
        }
        builder.setPositiveButton(this.getText(R.string.select)) { dialog, _ ->
            if (selectedItemIndex >= 0) {
                selection(selectedItemIndex, itemNames[selectedItemIndex], true)
                dialog.dismiss()
            }
        }
        if (showNegativeButton) {
            builder.setNegativeButton(this.getText(R.string.cancel)) { dialog, _ ->
                selection(selectedItemIndex, String(), false)
                dialog.dismiss()
            }
        }
        builder.show()
    }
}