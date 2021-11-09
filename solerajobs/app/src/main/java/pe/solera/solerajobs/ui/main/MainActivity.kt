package pe.solera.solerajobs.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.extension.addFragmentToNavigation
import pe.solera.core.extension.moveBackToFirstFragment
import pe.solera.core.extension.showMaterialDialog
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ActivityMainBinding
import pe.solera.solerajobs.ui.BaseActivity
import pe.solera.solerajobs.ui.HostFragment
import pe.solera.solerajobs.ui.main.chart.HostChartFragment
import pe.solera.solerajobs.ui.main.setup.HostSetupFragment
import pe.solera.solerajobs.ui.main.task.HostTaskListFragment

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding

    private var currentFragment : HostFragment? = null
    set(value) {
        field = value
        this.setBottomNavBasedOnCurrentFragment()
    }

    private var hostFragmentTasks : HostTaskListFragment = HostTaskListFragment()
    private var hostChartFragment : HostChartFragment = HostChartFragment()
    private var hostSetupFragment : HostSetupFragment = HostSetupFragment()

    companion object {
        var modifiedQuickAccess: MutableLiveData<Boolean> = MutableLiveData(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.onNavigationItemSelected()
        savedInstanceState?.let {
            this.clearFragmentBeforeInstanceState()
        }
        this.showFragmentOnFrameLayout(this.hostFragmentTasks)
    }

    private fun setBottomNavBasedOnCurrentFragment() {
        when(currentFragment) {
            is HostTaskListFragment -> { this.setBottomNavPosition(R.id.bottom_nav_item_tasks) }
            is HostChartFragment -> { this.setBottomNavPosition(R.id.bottom_nav_item_stadistics) }
            is HostSetupFragment -> { this.setBottomNavPosition(R.id.bottom_nav_item_config) }
        }
    }

    private fun setBottomNavPosition(itemId: Int) {
        this.binding.bottomNavMain.selectedItemId = itemId
    }

    private fun clearFragmentBeforeInstanceState() {
        try {
            this.supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun validateCurrentFragmentInstance(fragment: Fragment) {
        if (fragment is HostFragment) { this.currentFragment = fragment }
    }

    private fun onNavigationItemSelected() {
        binding.bottomNavMain.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bottom_nav_item_tasks -> this.showFragmentOnFrameLayout(this.hostFragmentTasks)
                R.id.bottom_nav_item_stadistics -> this.showFragmentOnFrameLayout(this.hostChartFragment)
                R.id.bottom_nav_item_config -> this.showFragmentOnFrameLayout(this.hostSetupFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun showFragmentOnFrameLayout(fragment: HostFragment) : Boolean {
        if (this.currentFragment != fragment) {
            this.supportFragmentManager.let {
                when(fragment) {
                    is HostTaskListFragment -> it.addFragmentToNavigation(fragment, HostTaskListFragment::class.java.name, R.id.fragmentContainerMain, this.currentFragment)
                    is HostChartFragment -> it.addFragmentToNavigation(fragment, HostChartFragment::class.java.name, R.id.fragmentContainerMain, this.currentFragment)
                    is HostSetupFragment -> it.addFragmentToNavigation(fragment, HostSetupFragment::class.java.name, R.id.fragmentContainerMain, this.currentFragment)
                }
                validateCurrentFragmentInstance(fragment)
            }
        } else {
            if (this.currentFragment?.isVisible == true) {
                //this.currentFragment?.backToFirstFragmentOfNavigation()
            }
        }
        return true
    }

    private fun askToLeaveApplication(showNegativeBtn: Boolean = true) {
        this.showMaterialDialog(
            message = this.getString(R.string.want_to_leave_app),
            acceptBtnMsg = getString(R.string.exit),
            showNegativeBtn = showNegativeBtn
        ) { action ->
            if (action) finish()
        }
    }

    override fun onBackPressed() {
        if (this.currentFragment?.backStackFragmentFromNavigation() != true) {
            this.supportFragmentManager.moveBackToFirstFragment(this.currentFragment, this.hostFragmentTasks)?.let {
                if (it !is HostFragment) {
                    this.askToLeaveApplication(false)
                }
                this.validateCurrentFragmentInstance(it)
            } ?: kotlin.run {
                this.askToLeaveApplication()
            }
        }
    }

}