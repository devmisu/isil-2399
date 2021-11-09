package pe.solera.repository.network.api.setup

import pe.solera.core.EventResult
import pe.solera.entity.QuickAccessForSelection
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.util.validateResponse
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SetupNetworkImpl(
    private val soleraJobsApi: SoleraJobsApi
) : SetupNetworkRepository {

    override suspend fun getQuickAccessForSelection(): EventResult<ArrayList<QuickAccessForSelection>> {
        val response = soleraJobsApi.getFavorites()
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    val parsedResponse = ArrayList<QuickAccessForSelection>()
                    it.map { response -> parsedResponse.add(response.toEntity()) }
                    continuation.resume(EventResult.Success(parsedResponse))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                }
            )
        }
    }

    override suspend fun saveQuickAccessSelected(list: ArrayList<QuickAccessForSelection>): EventResult<Boolean> {
        val request = ArrayList<String>()
        list.map { request.add(it.id) }
        val response = soleraJobsApi.setFavorites(request)
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