package pe.solera.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuickAccess(
    var name: String = String(),
    var client: QuickAccessItem = QuickAccessItem(),
    var project: QuickAccessItem = QuickAccessItem()
) : Parcelable