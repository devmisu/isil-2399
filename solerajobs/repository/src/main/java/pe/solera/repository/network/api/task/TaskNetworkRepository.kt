package pe.solera.repository.network.api.task

import pe.solera.core.EventResult
import pe.solera.entity.QuickAccess
import pe.solera.entity.TaskIdentifier
import pe.solera.entity.UserTask
import pe.solera.entity.UserWorkedDay
import java.util.*
import kotlin.collections.ArrayList

interface TaskNetworkRepository {

    suspend fun getUserTasksOfDay(date: Date) : EventResult<UserWorkedDay>

    suspend fun getUserTaskDetail(id: Int) : EventResult<UserTask>

    suspend fun editUserTaskDetail(userTask: UserTask) : EventResult<Boolean>

    suspend fun getTaskIdentifierList(taskIdentifier: TaskIdentifier) : EventResult<List<TaskIdentifier>>

    suspend fun createTask(userTask: UserTask) : EventResult<Boolean>

    suspend fun deleteTask(id: Int) : EventResult<Boolean>

    suspend fun getUserQuickAccessList() : EventResult<ArrayList<QuickAccess>>

    suspend fun snapHour(modifiedTask: Pair<Int, Double>) : EventResult<Boolean>

}