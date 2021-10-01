package pe.solera.repository.network.api.login

import pe.solera.core.EventResult
import pe.solera.entity.Authentication

interface LoginNetworkRepository {

    suspend fun sendGoogleIdToken(idToken: String) : EventResult<Authentication>

}