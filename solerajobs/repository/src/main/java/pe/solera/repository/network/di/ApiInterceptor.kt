package pe.solera.repository.network.di

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import pe.solera.core.ConstantsCore.Server
import pe.solera.core.ConstantsCore.Server.PLATFORM
import pe.solera.core.ConstantsCore.Server.X_DENSITY
import pe.solera.core.ConstantsCore.Server.X_HEIGHT
import pe.solera.core.ConstantsCore.Server.X_OS
import pe.solera.core.ConstantsCore.Server.X_VERSION
import pe.solera.core.ConstantsCore.Server.X_WIDTH

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request : Request = chain.request().newBuilder().let {
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