package pe.solera.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuickAccessItem(
    var id: Int = -1,
    var name: String = String()
) : Parcelable