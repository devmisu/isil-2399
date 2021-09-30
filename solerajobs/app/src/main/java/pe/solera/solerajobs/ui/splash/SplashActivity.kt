package pe.solera.solerajobs.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.solerajobs.databinding.ActivitySplashBinding

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    private val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.randomString = "randomString"
        setContentView(binding.root)
        viewModel.getUser()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userEventLiveData.observe(this) {
            when(it) {
                is SplashEventResult.SuccessUser -> {
                    binding.randomString = it.user.name
                }
            }
        }
    }
}
