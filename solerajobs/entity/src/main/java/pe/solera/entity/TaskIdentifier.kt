package pe.solera.entity

data class TaskIdentifier(
    var taskType: TaskType = TaskType.CLIENT,
    var taskParent: Int = 0,
    var name: String = String()
)