package pe.solera.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    var id: String = String(),
    var title: String = String()
) : Parcelable