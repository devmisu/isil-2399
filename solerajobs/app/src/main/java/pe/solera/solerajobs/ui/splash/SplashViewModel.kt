package pe.solera.solerajobs.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.solera.core.extension.launchOnIO
import pe.solera.repository.local.preferences.source.user.LoginPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val loginPreferencesRepository: LoginPreferencesRepository
): ViewModel() {

    private val splashEvent : MutableLiveData<SplashEventResult> = MutableLiveData()
    val splashEventLiveData : LiveData<SplashEventResult> get() = splashEvent

    fun validateSession() {
        splashEvent.value = SplashEventResult.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(1000)
                loginPreferencesRepository.validateSession()
            }.also {
                if (it) {
                    splashEvent.value = SplashEventResult.ValidSession
                } else {
                    splashEvent.value = SplashEventResult.NeedLogin
                }
            }
        }
    }
}