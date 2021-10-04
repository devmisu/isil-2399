package pe.solera.solerajobs.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.BaseException
import pe.solera.core.ConstantsCore
import pe.solera.core.extension.showToast
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ActivityLoginBinding
import pe.solera.solerajobs.ui.BaseActivity
import pe.solera.solerajobs.ui.dialog.ChildBottomSheetType
import pe.solera.solerajobs.ui.dialog.HostBottomSheet
import pe.solera.solerajobs.ui.main.MainActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding : ActivityLoginBinding

    private val viewModel : LoginViewModel by viewModels()

    private val requestSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient : GoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, requestSignInOptions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateUnAuthorized(intent)
        setSignInButton()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginEventLiveData.observe(this) {
            when(it) {
                is LoginEventResult.AccessApp -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginEventResult.Error -> validateException(it.ex) {
                    showToast(this)
                    setSignInButton()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        validateUnAuthorized(intent) {
            setSignInButton()
        }
    }

    private fun validateUnAuthorized(intent: Intent?, showButton: (() -> Unit)? = null) {
        val isUnAuthorized = intent?.getBooleanExtra(UNAUTHORIZED, false)
        if (isUnAuthorized == true) {
            viewModel.deleteUserData()
            this.intent.putExtra(UNAUTHORIZED, false)
            showButton?.invoke()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken
            viewModel.login(idToken ?: throw BaseException.UnAuthorizeException(getString(R.string.not_authorized)))
        } catch (ex: Exception) {
            ex.printStackTrace()
            setSignInButton()
        }
    }

    private fun setSignInButton() {
        HostBottomSheet.Builder()
            .setType(ChildBottomSheetType.Login)
            .setHideable(false)
            .setHostBottomSheetBehavior(BottomSheetBehavior.STATE_HALF_EXPANDED)
            .setActionListener<Boolean>(action = {
                signIn()
            })
            .buildAndShow(supportFragmentManager)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultRegistry.launch(signInIntent)
    }

    private val resultRegistry = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleSignInResult(task)
        } else {
            setSignInButton()
        }
    }
}