package pe.solera.entity

data class UserTask(
    var projectName: String = String(),
    var requirement: String = String(),
    var hours: Double = 0.0
)