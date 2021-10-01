package pe.solera.solerajobs.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.core.extension.launchOnIO
import pe.solera.repository.local.preferences.source.user.UserPreferencesRepository
import pe.solera.repository.network.api.login.LoginNetworkRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginNetworkRepository: LoginNetworkRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val loginEvent : MutableLiveData<LoginEventResult> = MutableLiveData()
    val loginEventLiveData : LiveData<LoginEventResult> get() = loginEvent

    fun login(idToken: String) {
        loginEvent.value = LoginEventResult.Loading
        launchOnIO(
            doTask = {
                loginNetworkRepository.sendGoogleIdToken(idToken)
            },
            result = {
                userPreferencesRepository.saveUserWithAuthToken(it)
                loginEvent.value = LoginEventResult.UserInfo(it.user)
            },
            error = {
                loginEvent.value = LoginEventResult.Error(it)
            }
        )
    }

}