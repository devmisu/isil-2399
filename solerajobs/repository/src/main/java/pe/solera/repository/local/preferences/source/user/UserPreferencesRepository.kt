package pe.solera.repository.local.preferences.source.user

import pe.solera.entity.Authentication

interface UserPreferencesRepository {

    fun saveUserWithAuthToken(authentication: Authentication) : Boolean

    fun deleteUserWithAuthToken() : Boolean

}