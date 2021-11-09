package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName

data class UserTaskCreationRequest(
    @SerializedName("requirementId")
    var requirementId: Int,
    @SerializedName("date")
    var date: String,
    @SerializedName("hours")
    var hours: Int,
    @SerializedName("comment")
    var comment: String? = null
)