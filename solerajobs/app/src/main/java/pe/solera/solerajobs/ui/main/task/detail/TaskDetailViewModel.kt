package pe.solera.solerajobs.ui.main.task.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.core.extension.launchOnIO
import pe.solera.entity.UserTask
import pe.solera.repository.network.api.task.TaskNetworkRepository
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel
@Inject
constructor(
    private val taskNetworkRepository: TaskNetworkRepository
): ViewModel() {

    private val taskDetailEvent : MutableLiveData<TaskDetailEventResult> = MutableLiveData()
    val taskDetailEventLiveData : MutableLiveData<TaskDetailEventResult> get() = taskDetailEvent

    var userTask : UserTask = UserTask()

    var userTaskModel : UserTaskModel = UserTaskModel()

    var isNewTask: Boolean = false

    val hours = arrayListOf<Double>(
        1.0, 2.0, 3.0, 4.0, 5.0,
        6.0, 7.0, 8.0, 9.0, 10.0,
        11.0, 12.0, 13.0, 14.0, 15.0,
        16.0, 17.0, 18.0, 19.0, 20.0,
        21.0, 22.0, 23.0, 24.0
    )

    val hoursNameList : Array<String> = hours.map { it.toString() }.toTypedArray()

    companion object {
        const val USER_TASK_ID = "USER_TASK_ID"
        const val NEW_USER_TASK_ID : Int = -1
    }

    fun getTaskDetail() {
        taskDetailEvent.value = TaskDetailEventResult.Loading
        launchOnIO(
            doTask = {
                taskNetworkRepository.getUserTaskDetail(userTask.id)
            },
            result = {
                this.userTask = it
                hours.mapIndexed { index, d ->
                    if (d == this.userTask.realHours) {
                        this.userTaskModel.hourSelected = Pair(index, d)
                        return@mapIndexed
                    }
                }
                this.userTaskModel.comment = this.userTask.comment
                taskDetailEvent.value = TaskDetailEventResult.SuccessTaskDetail(this.userTask)
            },
            error = {
                taskDetailEvent.value = TaskDetailEventResult.Error(it)
            }
        )
    }

    fun editTaskDetail() {
        taskDetailEvent.value = TaskDetailEventResult.LoadingEdition
        launchOnIO(
            doTask = {
                val request = UserTask(
                    id = userTask.id,
                    realHours = userTaskModel.hourSelected.second,
                    comment = userTaskModel.comment
                )
                taskNetworkRepository.editUserTaskDetail(request)
            },
            result = {
                taskDetailEvent.value = TaskDetailEventResult.SuccessEdition
            },
            error = {
                taskDetailEvent.value = TaskDetailEventResult.Error(it)
            }
        )
    }

    fun validateCreation(valid: () -> Unit, errorMessage: (String) -> Unit) {
        when {
            userTaskModel.hourSelected.second == 0.0 -> errorMessage("Debe seleccionar al menos 1 hora de esfuerzo.")
            else -> valid()
        }
    }
}