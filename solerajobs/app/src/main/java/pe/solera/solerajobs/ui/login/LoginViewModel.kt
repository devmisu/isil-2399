package pe.solera.solerajobs.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.solera.core.extension.launchOnIO
import pe.solera.repository.local.preferences.source.user.LoginPreferencesRepository
import pe.solera.repository.network.api.login.LoginNetworkRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginNetworkRepository: LoginNetworkRepository,
    private val loginPreferencesRepository: LoginPreferencesRepository
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
                loginPreferencesRepository.saveUserWithAuthToken(it)
                loginEvent.value = LoginEventResult.AccessApp(it.user)
            },
            error = {
                loginEvent.value = LoginEventResult.Error(it)
            }
        )
    }

    fun deleteUserData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loginPreferencesRepository.deleteUserWithAuthToken()
            }
        }
    }

}