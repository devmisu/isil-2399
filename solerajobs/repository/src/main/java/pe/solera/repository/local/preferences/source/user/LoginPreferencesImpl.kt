package pe.solera.repository.local.preferences.source.user

import com.google.gson.Gson
import pe.solera.core.EventResult
import pe.solera.entity.Authentication
import pe.solera.entity.User
import pe.solera.repository.local.preferences.manager.PreferencesManager
import pe.solera.repository.util.ConstantsRepository

class LoginPreferencesImpl(
    private val preferencesManager: PreferencesManager,
    private val gson: Gson
) : LoginPreferencesRepository {

    override fun saveUserWithAuthToken(authentication: Authentication): Boolean {
        return try {
            val data = gson.toJson(authentication)
            preferencesManager.setValue(ConstantsRepository.Preferences.PREFERENCE_USER_WITH_TOKEN, data)
            true
        } catch (ex: Exception) {
            false
        }
    }

    override fun getSavedUser(): EventResult<User> {
        return try {
            val authUser = preferencesManager.getString(ConstantsRepository.Preferences.PREFERENCE_USER_WITH_TOKEN)
            val authUserModel = gson.fromJson(authUser, Authentication::class.java)
            return EventResult.Success(authUserModel.user)
        } catch (ex: Exception) {
            EventResult.Error(ex)
        }
    }

    override fun deleteUserWithAuthToken(): Boolean {
        return try {
            preferencesManager.setValue(ConstantsRepository.Preferences.PREFERENCE_USER_WITH_TOKEN, String())
            true
        } catch (ex: Exception) {
            false
        }
    }

    override suspend fun validateSession(): Boolean {
        return try {
            preferencesManager.getString(ConstantsRepository.Preferences.PREFERENCE_USER_WITH_TOKEN).isNotEmpty()
        } catch (ex: Exception) {
            false
        }
    }
}