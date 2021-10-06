package pe.solera.repository.network.api.task

import pe.solera.core.ConstantsCore
import pe.solera.core.EventResult
import pe.solera.entity.UserTask
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.util.validateResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskNetworkImpl(
    private val soleraJobsApi: SoleraJobsApi
) : TaskNetworkRepository {

    override suspend fun getUserTasksOfDay(date: Date): EventResult<List<UserTask>> {
        val formatter = SimpleDateFormat("YYYY-MM-dd", ConstantsCore.Locale.Spanish)
        val response = soleraJobsApi.getUserTasks(formatter.format(date))
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    val mapper = UserTaskResponseMapper()
                    val parsedResponse : List<UserTask> = it.map { rsp -> mapper.mapToEntity(rsp) }
                    continuation.resume(EventResult.Success(parsedResponse))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                }
            )
        }
    }
}