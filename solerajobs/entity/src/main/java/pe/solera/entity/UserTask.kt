package pe.solera.entity

data class UserTask(
    var id: Int = 0,
    var clientName : String = String(),
    var projectName: String = String(),
    var requirementDescription: String = String(),
    var projectManager: String = String(),
    var estimateStartDate: String = String(),
    var estimateEndDate: String = String(),
    var estimateHours : Double = 0.0,
    var realHours: Double = 0.0,
    var comment: String = String()
)