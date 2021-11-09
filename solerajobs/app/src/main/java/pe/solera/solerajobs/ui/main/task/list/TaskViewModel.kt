package pe.solera.solerajobs.ui.main.task.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pe.solera.core.extension.capitalized
import pe.solera.core.extension.launchOnIO
import pe.solera.core.extension.toTextualDate
import pe.solera.entity.QuickAccess
import pe.solera.entity.UserTask
import pe.solera.repository.local.preferences.source.user.LoginPreferencesRepository
import pe.solera.repository.network.api.task.TaskNetworkRepository
import pe.solera.solerajobs.ui.convertToHoursOfTask
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class TaskViewModel
@Inject
constructor(
    private val taskNetworkRepository: TaskNetworkRepository,
    private val loginPreferencesRepository: LoginPreferencesRepository
): ViewModel() {

    private val userTaskEvent : MutableLiveData<TaskListEventResult> = MutableLiveData()
    val userTaskEventLiveData: LiveData<TaskListEventResult> get() = userTaskEvent

    private var userTasksOfDay : ArrayList<UserTask> = ArrayList()

    private var currentDaySelected: Date = Date()

    private var changeDayJob : Job? = null

    var quickAccessList: ArrayList<QuickAccess> = ArrayList()

    fun getUserQuickAccessList() {
        launchOnIO(
            doTask = {
                taskNetworkRepository.getUserQuickAccessList()
            },
            result = {
                quickAccessList = it
            }
        )
    }

    fun getUserInfoAndTasksOfDay() {
        launchOnIO(
            doTask = {
                loginPreferencesRepository.getSavedUser()
            },
            result = {
                userTaskEvent.value = TaskListEventResult.UserInfo(it)
            },
            error = {
                userTaskEvent.value = TaskListEventResult.Error(it)
            }
        )
    }

    fun getUserTasksOfDay() {
        userTaskEvent.value = TaskListEventResult.Loading
        launchOnIO(
            doTask = {
                taskNetworkRepository.getUserTasksOfDay(currentDaySelected)
            },
            result = {
                userTasksOfDay = ArrayList(it.requirements)
                userTaskEvent.value = TaskListEventResult.UserTasksOfDayAndTotalHours(
                    convertToHoursOfTask(it.workedHours),
                    userTasksOfDay
                )
            },
            error = {
                userTaskEvent.value = TaskListEventResult.Error(it)
            }
        )
    }

    fun getTextualCurrentDay() : String {
        val day = currentDaySelected.toTextualDate("EEEE").replace('.', ' ').trim().capitalized()
        val numberDay = currentDaySelected.toTextualDate("dd")
        val month = currentDaySelected.toTextualDate("MMMM").replace('.', ' ').trim().capitalized()
        return "$day $numberDay de $month"
    }

    fun backDay() {
        changeDayJob?.cancel()
        changeDayJob = viewModelScope.launch {
            delay(200)
            if (isActive) {
                val calendar = Calendar.getInstance()
                calendar.time = currentDaySelected
                calendar.add(Calendar.DATE, -1)
                currentDaySelected = calendar.time
                userTaskEvent.value = TaskListEventResult.CurrentDayModified(getTextualCurrentDay())
                getUserTasksOfDay()
            }
        }
    }
    fun nextDay() {
        changeDayJob?.cancel()
        changeDayJob = viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar.time = currentDaySelected
            calendar.add(Calendar.DATE, 1)
            currentDaySelected = calendar.time
            userTaskEvent.value = TaskListEventResult.CurrentDayModified(getTextualCurrentDay())
            getUserTasksOfDay()
        }
    }
}