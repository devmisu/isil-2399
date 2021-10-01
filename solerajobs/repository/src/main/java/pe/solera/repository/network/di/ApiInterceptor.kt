package pe.solera.repository.network.di

import android.content.Context
import android.os.Build
import com.google.gson.Gson
import com.pddstudio.preferences.encrypted.EncryptedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import pe.solera.core.BaseException
import pe.solera.core.ConstantsCore.Server
import pe.solera.core.ConstantsCore.Server.AUTHORIZATION
import pe.solera.core.ConstantsCore.Server.PLATFORM
import pe.solera.core.ConstantsCore.Server.X_DENSITY
import pe.solera.core.ConstantsCore.Server.X_HEIGHT
import pe.solera.core.ConstantsCore.Server.X_OS
import pe.solera.core.ConstantsCore.Server.X_VERSION
import pe.solera.core.ConstantsCore.Server.X_WIDTH
import pe.solera.core.extension.isAirplaneModeActive
import pe.solera.core.extension.isConnected
import pe.solera.entity.Authentication
import pe.solera.repository.local.preferences.manager.PreferencesManager
import pe.solera.repository.util.ConstantsRepository
import solera.repository.R

class ApiInterceptor(
    private val context: Context,
    private val preferences: PreferencesManager,
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (context.isAirplaneModeActive()) {
            throw BaseException.AirplaneException(context.getString(R.string.airplane_error))
        } else if (!context.isConnected()) {
            throw BaseException.NetworkException(context.getString(R.string.network_error))
        } else {
            val authUser = preferences.getString(ConstantsRepository.Preferences.PREFERENCE_USER_WITH_TOKEN)
            val authUserModel = gson.fromJson(authUser, Authentication::class.java)
            val token = authUserModel.token
            val request : Request = chain.request().newBuilder().let {
                if (token.trim().isNotEmpty()) { it.header(AUTHORIZATION, token) }
                it.header(X_OS, PLATFORM)
                it.header(X_VERSION, Build.VERSION.SDK_INT.toString())
                //it.header(X_APP, BuildConfig.VERSION_NAME) inject
                //it.header(X_DENSITY, context.getDensity().toString())
                //it.header(X_WIDTH, context.getWidth().toString())
                //it.header(X_HEIGHT, context.getHeight().toString())
                it.build()
            }
            return chain.proceed(request)
        }
    }
}