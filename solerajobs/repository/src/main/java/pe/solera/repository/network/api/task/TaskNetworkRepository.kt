package pe.solera.repository.network.api.task

import pe.solera.core.EventResult
import pe.solera.entity.UserTask
import pe.solera.entity.UserWorkedDay
import java.util.*

interface TaskNetworkRepository {

    suspend fun getUserTasksOfDay(date: Date) : EventResult<UserWorkedDay>

    suspend fun getUserTaskDetail(id: Int) : EventResult<UserTask>

    suspend fun editUserTaskDetail(userTask: UserTask) : EventResult<Boolean>

}