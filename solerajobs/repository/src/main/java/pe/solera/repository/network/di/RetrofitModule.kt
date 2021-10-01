package pe.solera.repository.network.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.solera.core.ConstantsCore.Server.CACHE_SIZE
import pe.solera.core.ConstantsCore.Server.TIMEOUT
import pe.solera.repository.local.preferences.manager.PreferencesManager
import pe.solera.repository.network.api.SoleraJobsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import solera.repository.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideApi(
        retrofit: Retrofit
    ) : SoleraJobsApi {
        return retrofit.create(SoleraJobsApi::class.java)
    }

    @Provides
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String,
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiInterceptor: ApiInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiInterceptor)
            .build()
    }

    @Provides
    fun provideApiInterceptor(
        @ApplicationContext context: Context,
        preferencesManager: PreferencesManager,
        gson: Gson
    ) : ApiInterceptor {
        return ApiInterceptor(context, preferencesManager, gson)
    }

    @Provides
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    fun provideCache(
        @ApplicationContext context: Context
    ) : Cache {
        val cacheSize: Long = CACHE_SIZE
        return Cache(context.cacheDir, cacheSize)
    }

}