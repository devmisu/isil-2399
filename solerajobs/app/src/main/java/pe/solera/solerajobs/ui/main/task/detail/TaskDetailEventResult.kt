package pe.solera.solerajobs.ui.main.task.detail

import pe.solera.entity.TaskType
import pe.solera.entity.UserTask

sealed class TaskDetailEventResult(val isLoading: Boolean) {

    object Loading : TaskDetailEventResult(true)
    data class Error(val ex: Exception) : TaskDetailEventResult(false)
    data class SuccessTaskDetail(val userTask: UserTask) : TaskDetailEventResult(false)
    object LoadingEdition : TaskDetailEventResult(true)
    object SuccessEdition : TaskDetailEventResult(false)
    data class LoadingTaskIdentifier(val message: String) : TaskDetailEventResult(true)
    data class SuccessTaskIdentifier(val type: TaskType) : TaskDetailEventResult(false)
    object LoadingCreateTask : TaskDetailEventResult(true)
    object SuccessCreateTask : TaskDetailEventResult(false)

}