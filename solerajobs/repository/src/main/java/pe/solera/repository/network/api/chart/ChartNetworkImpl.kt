package pe.solera.repository.network.api.chart

import pe.solera.core.EventResult
import pe.solera.entity.Project
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.util.validateResponse
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChartNetworkImpl(
    private val soleraJobsApi: SoleraJobsApi
) : ChartNetworkRepository {

    override suspend fun getStatistics(startDate: String, endDate: String): EventResult<ArrayList<Project>> {
        val response = soleraJobsApi.getChart(
            startDate = startDate,
            endDate = endDate
        )
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    val parsedResponse = ArrayList<Project>()
                    it.map { model -> parsedResponse.add(model.toEntity()) }
                    continuation.resume(EventResult.Success(parsedResponse))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                }
            )
        }
    }
}