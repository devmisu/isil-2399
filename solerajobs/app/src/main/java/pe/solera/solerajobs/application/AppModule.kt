package pe.solera.solerajobs.application

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.solera.solerajobs.R
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(
        @ApplicationContext context: Context
    ) : String {
        return context.getString(R.string.base_url)
    }

    @Singleton
    @Provides
    @Named("encryptPass")
    fun provideEncryptPass(
        @ApplicationContext context: Context
    ) : String {
        return context.getString(R.string.encryptPass)
    }

}