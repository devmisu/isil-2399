package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName

data class TaskIdentifierResponse(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = String()
)