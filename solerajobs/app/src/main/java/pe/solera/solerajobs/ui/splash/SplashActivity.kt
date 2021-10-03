package pe.solera.solerajobs.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.solerajobs.databinding.ActivitySplashBinding
import pe.solera.solerajobs.ui.BaseActivity
import pe.solera.solerajobs.ui.login.LoginActivity
import pe.solera.solerajobs.ui.main.MainActivity

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding : ActivitySplashBinding

    private val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.validateSession()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.splashEventLiveData.observe(this) {
            when(it) {
                SplashEventResult.ValidSession -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                SplashEventResult.NeedLogin -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }

}
