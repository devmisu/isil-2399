package pe.solera.repository.network.api.task

import pe.solera.core.ConstantsCore
import pe.solera.core.EventResult
import pe.solera.entity.UserTask
import pe.solera.entity.UserWorkedDay
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.util.validateResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskNetworkImpl(
    private val soleraJobsApi: SoleraJobsApi
) : TaskNetworkRepository {

    override suspend fun getUserTasksOfDay(date: Date): EventResult<UserWorkedDay> {
        val formatter = SimpleDateFormat("YYYY-MM-dd", ConstantsCore.Locale.Spanish)
        val response = soleraJobsApi.getUserTasks(formatter.format(date))
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    val mapper = UserTaskResponseMapper()
                    val parsedResponse : UserWorkedDay = mapper.mapToEntity(it)
                    continuation.resume(EventResult.Success(parsedResponse))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                }
            )
        }
    }

    override suspend fun getUserTaskDetail(id: Int): EventResult<UserTask> {
        val response = soleraJobsApi.getUserTaskDetail(id)
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    val mapper = UserTaskResponseMapper()
                    val parsedResponse : UserTask = mapper.userTaskResponseToEntity(it)
                    continuation.resume(EventResult.Success(parsedResponse))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                }
            )
        }
    }

    override suspend fun editUserTaskDetail(userTask: UserTask): EventResult<Boolean> {
        val request = UserTaskRequest(hours = userTask.realHours, comment = userTask.comment)
        val response = soleraJobsApi.updateUserTask(userTask.id, request)
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    continuation.resume(EventResult.Success(true))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                },
                successWithoutBody = {
                    continuation.resume(EventResult.Success(true))
                }
            )
        }
    }
}