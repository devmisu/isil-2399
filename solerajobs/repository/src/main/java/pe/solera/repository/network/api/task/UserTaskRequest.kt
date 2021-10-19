package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName

data class UserTaskRequest(
    @SerializedName("hours")
    val hours: Double,
    @SerializedName("comment")
    val comment: String
)