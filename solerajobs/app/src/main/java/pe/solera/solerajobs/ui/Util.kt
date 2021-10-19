package pe.solera.solerajobs.ui

import android.content.Context
import android.content.Intent
import pe.solera.core.BaseException
import pe.solera.core.ConstantsCore
import pe.solera.solerajobs.ui.login.LoginActivity
import java.util.*

fun Context.validateException(ex: Exception, errorMessage: String.() -> Unit) {
    when(ex) {
        is BaseException.UnAuthorizeException -> {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                putExtra(BaseActivity.UNAUTHORIZED, true)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
        }
        else -> {
            errorMessage.invoke(ex.message ?: ConstantsCore.Error.Message.generalErrorMessage)
        }
    }
}

fun convertToHoursOfTask(hours: Double) : String {
    val quantity : String = if (hours > 1) "Horas" else "Hora"
    return "${hours.toInt()}:00 $quantity"
}

fun getDateFromFuture(days: Int): Long {
    val now = Calendar.getInstance()
    now.add(Calendar.DATE, days)
    now.set(Calendar.HOUR_OF_DAY, 0)
    now.set(Calendar.MINUTE, 0)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)
    return now.timeInMillis
}