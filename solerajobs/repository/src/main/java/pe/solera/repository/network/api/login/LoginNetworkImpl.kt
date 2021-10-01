package pe.solera.repository.network.api.login

import pe.solera.core.EventResult
import pe.solera.entity.Authentication
import pe.solera.entity.User
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.util.validateResponse
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginNetworkImpl(
    private val soleraJobsApi: SoleraJobsApi
) : LoginNetworkRepository {

    override suspend fun sendGoogleIdToken(idToken: String): EventResult<Authentication> {
        val response = soleraJobsApi.login(LoginRequest(idToken))
        return suspendCoroutine { continuation ->
            response.validateResponse(
                success = {
                    continuation.resume(EventResult.Success(
                        LoginResponseMapper().mapToEntity(it)
                    ))
                },
                error = {
                    continuation.resume(EventResult.Error(it))
                }
            )
        }
    }
}