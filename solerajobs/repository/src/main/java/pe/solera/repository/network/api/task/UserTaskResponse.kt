package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName

data class UserTaskResponse(
    @SerializedName("projectName")
    var projectName: String?,
    @SerializedName("requirement")
    var requirement: String?,
    @SerializedName("hours")
    var hours: Double?
)