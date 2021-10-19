package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName

data class WorkedDayResponse(
    @SerializedName("workedHours")
    var workedHours: Double?,
    @SerializedName("requirements")
    var requirements: ArrayList<UserTaskResponse>
)