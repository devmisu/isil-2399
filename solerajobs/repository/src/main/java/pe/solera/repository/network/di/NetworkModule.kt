package pe.solera.repository.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.network.api.app.UserNetworkRepository
import pe.solera.repository.network.api.app.UserNetworkImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideUserNetwork(
        soleraJobsApi: SoleraJobsApi
    ) : UserNetworkRepository = UserNetworkImpl(soleraJobsApi)

}