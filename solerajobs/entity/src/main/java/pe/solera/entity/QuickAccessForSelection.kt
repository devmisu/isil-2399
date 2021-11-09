package pe.solera.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuickAccessForSelection(
    var id: String = String(),
    var title: String = String(),
    var description: String = String(),
    var selected: Boolean = false
) : Parcelable