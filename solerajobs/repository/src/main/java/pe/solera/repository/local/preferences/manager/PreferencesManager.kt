package pe.solera.repository.local.preferences.manager

import com.pddstudio.preferences.encrypted.EncryptedPreferences

class PreferencesManager(
    private val encryptPreferences: EncryptedPreferences
) {

    fun setValue(key: String, value: String) {
        encryptPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun setValue(key: String, value: Int) {
        encryptPreferences
            .edit()
            .putInt(key, value)
            .apply()
    }

    fun setValue(key: String, value: Boolean) {
        encryptPreferences
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    fun setValue(key: String, value: Float) {
        encryptPreferences
            .edit()
            .putFloat(key, value)
            .apply()
    }

    fun setValue(key: String, value: Long) {
        encryptPreferences
            .edit()
            .putLong(key, value)
            .apply()
    }

    fun getString(key: String): String {
        return encryptPreferences.getString(key, String()) ?: String()
    }

    fun getInt(key: String): Int {
        return encryptPreferences.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return encryptPreferences.getBoolean(key, false)
    }

    fun getFloat(key: String): Float {
        return encryptPreferences.getFloat(key, 0f)
    }

    fun getLong(key: String): Long {
        return encryptPreferences.getLong(key, 0)
    }

    fun clearData() {
        encryptPreferences.edit().clear().apply()
    }

}