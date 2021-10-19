package pe.solera.solerajobs.ui.main.task.list

import pe.solera.entity.User
import pe.solera.entity.UserTask

sealed class TaskListEventResult(val isLoading: Boolean) {
    object Loading : TaskListEventResult(true)
    data class UserInfo(val user: User) : TaskListEventResult(false)
    data class UserTasksOfDayAndTotalHours(val totalHours: String, val tasks: ArrayList<UserTask>) : TaskListEventResult(false)
    data class CurrentDayModified(val day: String) : TaskListEventResult(false)
    data class Error(val ex: Exception) : TaskListEventResult(false)
}
