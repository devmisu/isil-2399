package pe.solera.repository.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.solera.repository.network.api.SoleraJobsApi
import pe.solera.repository.network.api.app.UserNetworkImpl
import pe.solera.repository.network.api.app.UserNetworkRepository
import pe.solera.repository.network.api.login.LoginNetworkImpl
import pe.solera.repository.network.api.login.LoginNetworkRepository
import pe.solera.repository.network.api.setup.SetupNetworkImpl
import pe.solera.repository.network.api.setup.SetupNetworkRepository
import pe.solera.repository.network.api.task.TaskNetworkImpl
import pe.solera.repository.network.api.task.TaskNetworkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideUserNetwork(
        soleraJobsApi: SoleraJobsApi
    ) : UserNetworkRepository = UserNetworkImpl(soleraJobsApi)

    @Singleton
    @Provides
    fun provideLoginNetwork(
        soleraJobsApi: SoleraJobsApi
    ) : LoginNetworkRepository = LoginNetworkImpl(soleraJobsApi)

    @Singleton
    @Provides
    fun provideTaskNetwork(
        soleraJobsApi: SoleraJobsApi
    ) : TaskNetworkRepository = TaskNetworkImpl(soleraJobsApi)

    @Singleton
    @Provides
    fun provideSetupNetwork(
        soleraJobsApi: SoleraJobsApi
    ) : SetupNetworkRepository = SetupNetworkImpl(soleraJobsApi)

}