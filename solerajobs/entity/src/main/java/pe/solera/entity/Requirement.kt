package pe.solera.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Requirement(
    var id: Int = 0,
    var description: String = String(),
    var startDate: String = String(),
    var endDate: String = String(),
    var estimateHours: Double = 0.0,
    var realHours: Double = 0.0,
    var comment: String = String()
) : Parcelable
