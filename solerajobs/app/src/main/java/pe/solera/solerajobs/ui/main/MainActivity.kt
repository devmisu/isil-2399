package pe.solera.solerajobs.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}