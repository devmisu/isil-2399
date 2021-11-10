package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName

data class UpdateTaskRequest(
    @SerializedName("hours")
    var hours: Double = 0.0
)