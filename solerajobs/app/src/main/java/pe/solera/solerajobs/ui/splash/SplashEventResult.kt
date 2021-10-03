package pe.solera.solerajobs.ui.splash

sealed class SplashEventResult(var isLoading: Boolean) {
    object Loading : SplashEventResult(true)
    data class Error(var exception: Exception) : SplashEventResult(false)
    object ValidSession : SplashEventResult(false)
    object NeedLogin : SplashEventResult(false)
}
