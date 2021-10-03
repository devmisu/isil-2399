package pe.solera.repository.local.preferences.source.user

import pe.solera.core.EventResult
import pe.solera.entity.Authentication
import pe.solera.entity.User

interface LoginPreferencesRepository {

    fun saveUserWithAuthToken(authentication: Authentication) : Boolean

    fun getSavedUser() : EventResult<User>

    fun deleteUserWithAuthToken() : Boolean

    suspend fun validateSession() : Boolean

}