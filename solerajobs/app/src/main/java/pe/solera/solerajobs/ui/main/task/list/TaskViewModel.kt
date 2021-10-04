package pe.solera.solerajobs.ui.main.task.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.core.extension.launchOnIO
import pe.solera.entity.UserTask
import pe.solera.repository.network.api.task.TaskNetworkRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class TaskViewModel
@Inject
constructor(
    private val taskNetworkRepository: TaskNetworkRepository
): ViewModel() {

    private val userTaskEvent : MutableLiveData<TaskListEventResult> = MutableLiveData()
    val userTaskEventLiveData: LiveData<TaskListEventResult> get() = userTaskEvent

    private var userTasksOfDay : ArrayList<UserTask> = ArrayList()

    fun getUserTasksOfDay(date: Date) {
        userTaskEvent.value = TaskListEventResult.Loading
        launchOnIO(
            doTask = {
                taskNetworkRepository.getUserTasksOfDay(date)
            },
            result = {
                userTasksOfDay = ArrayList(it)
                userTaskEvent.value = TaskListEventResult.UserTasksOfDay(userTasksOfDay)
            },
            error = {
                userTaskEvent.value = TaskListEventResult.Error(it)
            }
        )
    }

}