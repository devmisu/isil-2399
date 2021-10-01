package pe.solera.repository.local.preferences.source.user

import com.google.gson.Gson
import pe.solera.entity.Authentication
import pe.solera.repository.local.preferences.manager.PreferencesManager
import pe.solera.repository.util.ConstantsRepository

class UserPreferencesImpl(
    private val preferencesManager: PreferencesManager,
    private val gson: Gson
) : UserPreferencesRepository {

    override fun saveUserWithAuthToken(authentication: Authentication): Boolean {
        return try {
            val data = gson.toJson(authentication)
            preferencesManager.setValue(ConstantsRepository.Preferences.PREFERENCE_USER_WITH_TOKEN, data)
            true
        } catch (ex: Exception) {
            false
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
}