package pe.solera.core.extension

import pe.solera.core.ConstantsCore
import java.text.SimpleDateFormat
import java.util.*

infix fun String?.toDate(pattern: String) : Date {
    return this?.let {
        try {
            val formatter = SimpleDateFormat(pattern, ConstantsCore.Locale.Spanish)
            formatter.parse(this)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Date()
        }
    } ?: Date()
}

fun Date?.toTextualDate(pattern: String) : String {
    val formatter = SimpleDateFormat(pattern, ConstantsCore.Locale.Spanish)
    return try {
        formatter.format(this!!)
    } catch (ex: Exception) {
        formatter.format(Date())
    }
}