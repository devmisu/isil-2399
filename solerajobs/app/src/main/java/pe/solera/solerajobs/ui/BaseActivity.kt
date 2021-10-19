package pe.solera.solerajobs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import pe.solera.core.BaseException
import pe.solera.core.ConstantsCore
import pe.solera.solerajobs.ui.login.LoginActivity

open class BaseActivity : AppCompatActivity() {

    var isLoading : Boolean = false

    companion object {
        const val UNAUTHORIZED = "UNAUTHORIZED"
    }

    fun validateException(ex: Exception, errorMessage: String.() -> Unit) {
        when(ex) {
            is BaseException.UnAuthorizeException -> {
                startActivity(Intent(this, LoginActivity::class.java).apply {
                    putExtra(UNAUTHORIZED, true)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
                })
            }
            else -> {
                errorMessage.invoke(ex.message ?: ConstantsCore.Error.Message.generalErrorMessage)
            }
        }
    }

}