package pe.solera.solerajobs.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentHostBinding

abstract class HostFragment : Fragment() {

    lateinit var binding : FragmentHostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_host, container, false)
        this.setNavigationOptions()
        return this.binding.root
    }

    abstract fun navGraphResource() : Int

    private fun setNavigationOptions() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.hostFragmentContainerView) as NavHostFragment?
        navHostFragment?.let {
            val inflater = it.navController.navInflater
            val graph = inflater.inflate(navGraphResource())
            it.navController.graph = graph
        } ?: kotlin.run {
            Log.d("navHost", "can't cast")
        }
    }

    private fun validateOnlyOneItemOnNavigation() : Boolean {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.hostFragmentContainerView) as NavHostFragment?
        return navHostFragment?.childFragmentManager?.backStackEntryCount ?: 0 < 1
    }

    fun backStackFragmentFromNavigation() : Boolean {
        try {
            val navHostFragment = childFragmentManager.findFragmentById(R.id.hostFragmentContainerView) as NavHostFragment?
            navHostFragment?.let {
                return if (!validateOnlyOneItemOnNavigation()) {
                    it.navController.navigateUp()
                    true
                } else false
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

}