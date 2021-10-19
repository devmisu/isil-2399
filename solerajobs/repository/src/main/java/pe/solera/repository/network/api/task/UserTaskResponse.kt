package pe.solera.repository.network.api.task

import com.google.gson.annotations.SerializedName

data class UserTaskResponse(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("clientName")
    var clientName : String?,
    @SerializedName("projectName")
    var projectName: String?,
    @SerializedName("requirementDescription")
    var requirementDescription: String?,
    @SerializedName("projectManager")
    var projectManager: String?,
    @SerializedName("estimateStartDate")
    var estimateStartDate: String?,
    @SerializedName("estimateEndDate")
    var estimateEndDate: String?,
    @SerializedName("estimateHours")
    var estimateHours : Double?,
    @SerializedName("realHours")
    var realHours: Double?,
    @SerializedName("comment")
    var comment: String?
)