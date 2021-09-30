package pe.solera.solerajobs.ui.splash

import pe.solera.entity.User

sealed class SplashEventResult(var isLoading: Boolean) {
    object Loading : SplashEventResult(true)
    data class Error(var exception: Exception) : SplashEventResult(false)
    data class SuccessUser(var user: User) : SplashEventResult(false)
}
