package pe.solera.repository.network.api.app

import pe.solera.core.EventResult
import pe.solera.entity.User

interface UserNetworkRepository {

    suspend fun getUserData() : EventResult<User>

}