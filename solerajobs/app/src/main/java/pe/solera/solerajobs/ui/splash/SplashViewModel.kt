package pe.solera.solerajobs.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.core.extension.launchOnIO
import pe.solera.repository.network.api.app.UserNetworkRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val userNetworkRepository: UserNetworkRepository
): ViewModel() {

    private val userEvent : MutableLiveData<SplashEventResult> = MutableLiveData()
    val userEventLiveData : LiveData<SplashEventResult> get() = userEvent

    fun getUser() {
        userEvent.value = SplashEventResult.Loading
        launchOnIO(
            doTask = {
                userNetworkRepository.getUserData()
            },
            result = {
                userEvent.value = SplashEventResult.SuccessUser(it)
            }
        )
    }

}