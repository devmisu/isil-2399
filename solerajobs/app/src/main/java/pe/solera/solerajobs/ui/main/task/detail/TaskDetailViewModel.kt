package pe.solera.solerajobs.ui.main.task.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.core.ConstantsCore
import pe.solera.core.extension.launchOnIO
import pe.solera.core.extension.toTextualDate
import pe.solera.entity.TaskIdentifier
import pe.solera.entity.TaskType
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

    var clientList      : ArrayList<TaskIdentifier> = ArrayList()
    var projectList     : ArrayList<TaskIdentifier> = ArrayList()
    var requirementList : ArrayList<TaskIdentifier> = ArrayList()

    val hours = arrayListOf<Double>(
        1.0, 2.0, 3.0, 4.0, 5.0,
        6.0, 7.0, 8.0, 9.0, 10.0,
        11.0, 12.0, 13.0, 14.0, 15.0,
        16.0, 17.0, 18.0, 19.0, 20.0,
        21.0, 22.0, 23.0, 24.0
    )

    fun clientsNameList() : Array<String> {
        val list = ArrayList<String>()
        clientList.map { list.add(it.name) }
        return list.toTypedArray()
    }
    fun projectsNameList() : Array<String> {
        val list = ArrayList<String>()
        projectList.map { list.add(it.name) }
        return list.toTypedArray()
    }
    fun requirementsNameList() : Array<String> {
        val list = ArrayList<String>()
        requirementList.map { list.add(it.name) }
        return list.toTypedArray()
    }
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

    fun getListByTaskType(taskIdentifier: TaskIdentifier) {
        taskDetailEvent.value = TaskDetailEventResult.LoadingTaskIdentifier(when(taskIdentifier.taskType) {
            TaskType.CLIENT -> "Cargando lista de clientes..."
            TaskType.PROJECT -> "Cargando lista de proyectos..."
            TaskType.REQUIREMENT -> "Cargando lista de requerimientos..."
        })
        launchOnIO(
            doTask = {
                taskNetworkRepository.getTaskIdentifierList(taskIdentifier)
            },
            result = {
                when(taskIdentifier.taskType) {
                    TaskType.CLIENT -> {
                        clientList = ArrayList(it)
                        userTaskModel.clientIdentifier = Pair(-1, TaskIdentifier())
                        userTaskModel.projectIdentifier = Pair(-1, TaskIdentifier())
                        userTaskModel.requirementIdentifier = Pair(-1, TaskIdentifier())
                    }
                    TaskType.PROJECT -> {
                        projectList = ArrayList(it)
                        userTaskModel.projectIdentifier = Pair(-1, TaskIdentifier())
                        userTaskModel.requirementIdentifier = Pair(-1, TaskIdentifier())
                    }
                    TaskType.REQUIREMENT -> {
                        requirementList = ArrayList(it)
                        userTaskModel.requirementIdentifier = Pair(-1, TaskIdentifier())
                    }
                }
                taskDetailEvent.value = TaskDetailEventResult.SuccessTaskIdentifier(taskIdentifier.taskType)
            },
            error = {
                taskDetailEvent.value = TaskDetailEventResult.Error(it)
            }
        )
    }

    fun createTask() {
        taskDetailEvent.value = TaskDetailEventResult.LoadingCreateTask
        launchOnIO(
            doTask = {
                val request = UserTask(
                    id = userTaskModel.requirementIdentifier.second.taskParent,
                    realHours = userTaskModel.hourSelected.second,
                    comment = userTaskModel.comment,
                    estimateStartDate = userTaskModel.date.toTextualDate(ConstantsCore.DatePattern.yyyyMMdd)
                )
                taskNetworkRepository.createTask(request)
            },
            result = {
                taskDetailEvent.value = TaskDetailEventResult.SuccessCreateTask
            },
            error = {
                taskDetailEvent.value = TaskDetailEventResult.Error(it)
            }
        )
    }

    fun validateCreation(valid: () -> Unit, errorMessage: (String) -> Unit) {
        when {
            userTaskModel.requirementIdentifier.first == -1 -> errorMessage("Debe seleccionar un requerimiento")
            userTaskModel.date == null -> errorMessage("Debe ingresar un fecha")
            userTaskModel.hourSelected.second == 0.0 -> errorMessage("Debe seleccionar al menos 1 hora de esfuerzo.")
            else -> valid()
        }
    }

    fun deleteTask() {
        taskDetailEvent.value = TaskDetailEventResult.LoadingDelete
        launchOnIO(
            doTask = {
                taskNetworkRepository.deleteTask(userTask.id)
            },
            result = {
                taskDetailEvent.value = TaskDetailEventResult.SuccessEdition
            },
            error = {
                taskDetailEvent.value = TaskDetailEventResult.Error(it)
            }
        )
    }
}