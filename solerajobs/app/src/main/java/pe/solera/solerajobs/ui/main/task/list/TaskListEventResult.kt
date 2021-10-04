package pe.solera.solerajobs.ui.main.task.list

import pe.solera.entity.UserTask

sealed class TaskListEventResult(val isLoading: Boolean) {
    object Loading : TaskListEventResult(true)
    data class UserTasksOfDay(val tasks: ArrayList<UserTask>) : TaskListEventResult(false)
    data class Error(val ex: Exception) : TaskListEventResult(false)
}
