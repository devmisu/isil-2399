package pe.solera.core.extension

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import pe.solera.core.BaseException
import pe.solera.core.ConstantsCore

fun View?.setAnyBackgroundTintColor(colorIdOrHex: Any) {
    this?.let {
        try {
            when (colorIdOrHex) {
                is Int    -> this.backgroundTintList = ContextCompat.getColorStateList(this.context, colorIdOrHex)
                is String -> this.backgroundTintCompatibility(colorIdOrHex)
                else      -> this.setBackgroundColor(Color.BLUE)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.d(ConstantsCore.problemLog, ex.message ?: String())
        }
    } ?: run {
        Log.d(ConstantsCore.problemLog, ConstantsCore.Error.View.cantModifyBackground)
    }
}

fun ImageView?.setImageTintColor(colorIdOrHex: Any) {
    this?.let {
        try {
            when (colorIdOrHex) {
                is Int    -> this.imageTintList = ContextCompat.getColorStateList(this.context, colorIdOrHex)
                is String -> this.imageTintCompatibility(colorIdOrHex)
                else      -> throw BaseException.ResourceException()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.d(ConstantsCore.problemLog, ex.message ?: String())
        }
    } ?: run {
        Log.d(ConstantsCore.problemLog, ConstantsCore.Error.View.cantModifyBackground)
    }
}

fun View?.backgroundTintCompatibility(colorIdOrHex: String){
    this?.background?.setTint(Color.parseColor(colorIdOrHex))
}

fun ImageView?.imageTintCompatibility(colorIdOrHex: String){
    this?.drawable?.setTint(Color.parseColor(colorIdOrHex))
}