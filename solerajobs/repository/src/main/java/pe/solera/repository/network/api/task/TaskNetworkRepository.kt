package pe.solera.repository.network.api.task

import pe.solera.core.EventResult
import pe.solera.entity.UserTask
import java.util.*

interface TaskNetworkRepository {

    suspend fun getUserTasksOfDay(date: Date) : EventResult<List<UserTask>>

}