package pe.solera.repository.network.api.chart

import com.google.gson.annotations.SerializedName
import pe.solera.entity.Project
import pe.solera.entity.Requirement

data class ProjectResponse(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("projectName")
    var projectName: String = String(),
    @SerializedName("clientName")
    var clientName: String = String(),
    @SerializedName("managerName")
    var managerName: String = String(),
    @SerializedName("totalEstimateHours")
    var totalEstimateHours: Double = 0.0,
    @SerializedName("totalWorkedHours")
    var totalWorkedHours: Double = 0.0,
    @SerializedName("requirements")
    var requirements: ArrayList<RequirementResponse>
)

data class RequirementResponse(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("description")
    var description: String = String(),
    @SerializedName("startDate")
    var startDate: String = String(),
    @SerializedName("endDate")
    var endDate: String = String(),
    @SerializedName("estimateHours")
    var estimateHours: Double = 0.0,
    @SerializedName("realHours")
    var realHours: Double = 0.0,
    @SerializedName("comment")
    var comment: String = String()
)

fun ProjectResponse.toEntity() : Project {
    val requirements = ArrayList<Requirement>()
    this.requirements.map { requirements.add(
        Requirement(
            id = it.id,
            description = it.description,
            startDate = it.startDate,
            endDate = it.endDate,
            estimateHours = it.estimateHours,
            realHours = it.realHours,
            comment = it.comment
        )
    ) }
    return Project(
        id = this.id,
        projectName = this.projectName,
        clientName = this.clientName,
        managerName = this.managerName,
        totalEstimateHours = this.totalEstimateHours,
        totalWorkedHours = this.totalWorkedHours,
        requirements = requirements
    )
}