package pe.solera.repository.local.preferences.di

import android.content.Context
import com.google.gson.Gson
import com.pddstudio.preferences.encrypted.EncryptedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.solera.repository.local.preferences.manager.PreferencesManager
import pe.solera.repository.local.preferences.source.user.UserPreferencesImpl
import pe.solera.repository.local.preferences.source.user.UserPreferencesRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun providePreferencesManager(
        encryptedPreferences: EncryptedPreferences
    ) : PreferencesManager {
        return PreferencesManager(encryptedPreferences)
    }

    @Singleton
    @Provides
    fun provideEncryptedPreferences(
        @ApplicationContext context: Context,
        @Named("encryptPass") encryptPass: String
    ) : EncryptedPreferences {
        return EncryptedPreferences.Builder(context)
            .withEncryptionPassword(encryptPass)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return Gson()
    }

    //Implementations

    @Singleton
    @Provides
    fun provideUserPreferences(
        preferencesManager: PreferencesManager,
        gson: Gson
    ) : UserPreferencesRepository = UserPreferencesImpl(preferencesManager, gson)

}