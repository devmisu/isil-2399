package pe.solera.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    var id: Int = 0,
    var projectName: String = String(),
    var clientName: String = String(),
    var managerName: String = String(),
    var totalEstimateHours: Double = 0.0,
    var totalWorkedHours: Double = 0.0,
    var requirements: ArrayList<Requirement> = ArrayList()
) : Parcelable
