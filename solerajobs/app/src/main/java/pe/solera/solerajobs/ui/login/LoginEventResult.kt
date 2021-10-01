package pe.solera.solerajobs.ui.login

import pe.solera.entity.User

sealed class LoginEventResult(val isLoading: Boolean) {
    object Loading : LoginEventResult(true)
    data class Error(val ex: Exception) : LoginEventResult(false)
    data class UserInfo(val user: User) : LoginEventResult(false)
}