package pe.solera.solerajobs.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.solera.solerajobs.databinding.ActivitySplashBinding
import pe.solera.solerajobs.ui.login.LoginActivity

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GlobalScope.launch {
            delay(1500)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }

}
