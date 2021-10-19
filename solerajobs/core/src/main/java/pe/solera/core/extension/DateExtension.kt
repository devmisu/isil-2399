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

//Offset timezone
infix fun Long.toDateString(pattern: String) : String {
    val timeZone = TimeZone.getDefault()
    val offsetFromUTC = timeZone.getOffset(Date().time).times(-1)
    val format = SimpleDateFormat(pattern, ConstantsCore.Locale.Spanish)
    val date = Date(this.plus(offsetFromUTC))
    return format.format(date)
}

//Offset timezone
infix fun Long.toDate(pattern: String) : Date {
    val timeZone = TimeZone.getDefault()
    val offsetFromUTC = timeZone.getOffset(Date().time).times(-1)
    return Date(this.plus(offsetFromUTC))
}