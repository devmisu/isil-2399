package pe.solera.repository.network.api.app

import pe.solera.core.EventResult
import pe.solera.entity.User
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.util.validateResponse
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserNetworkImpl(
    private val soleraJobsApi: SoleraJobsApi
) : UserNetworkRepository{

    override suspend fun getUserData(): EventResult<User> {
        val response = soleraJobsApi.getUser()
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    val userResponse = it.results.first()
                    continuation.resume(EventResult.Success(UserResponseMapper().mapToEntity(userResponse)))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                }
            )
        }
    }
}