package pe.solera.solerajobs.ui.main.setup.quickaccess

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.extension.showMaterialDialog
import pe.solera.entity.QuickAccessForSelection
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ActivityQuickAccessBinding
import pe.solera.solerajobs.ui.BaseActivity
import pe.solera.solerajobs.ui.util.selectable.SelectableAdapter
import pe.solera.solerajobs.ui.util.selectable.SelectableModel
import pe.solera.solerajobs.ui.validateException

@AndroidEntryPoint
class QuickAccessActivity : BaseActivity(), SelectableAdapter.SelectableAdapterListener<QuickAccessForSelection> {

    private lateinit var binding: ActivityQuickAccessBinding

    private val viewModel: QuickAccessViewModel by viewModels()

    private lateinit var adapter: QuickAccessAdapter

    private var alertDialog : AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuickAccessBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        viewModel.getQuickAccessListForSelection()
        observeViewModel()
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnSave.setOnClickListener {
            if (!isLoading) {
                viewModel.saveSelectedQuickAccessList()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.quickAccessEventLiveData.observe(this) {
            isLoading = it.isLoading
            when(it) {
                is QuickAccessEventResult.SuccessQuickAccess -> {
                    hideListLoader()
                    adapter = QuickAccessAdapter(this)
                    adapter.items = it.items
                    binding.rcvProject.adapter = adapter
                }
                is QuickAccessEventResult.Error -> {
                    hideListLoader()
                    binding.progressSave.visibility = View.GONE
                    validateException(it.ex) {
                        alertDialog?.hide()
                        alertDialog = showMaterialDialog(
                            message = this
                        )
                    }
                }
                QuickAccessEventResult.Loading -> {
                    showListLoader()
                }
                QuickAccessEventResult.SavingQuickAccess -> {
                    binding.progressSave.visibility = View.VISIBLE
                }
                QuickAccessEventResult.SuccessSaveQuickAccess -> {
                    binding.progressSave.visibility = View.GONE
                    alertDialog?.hide()
                    alertDialog = showMaterialDialog(
                        message = this.getString(R.string.success_save_quickaccess),
                        acceptBtnMsg = getString(R.string.exit),
                        showNegativeBtn = false
                    ) { _ ->
                        finish()
                    }
                }
            }
        }
    }

    private fun showListLoader() {
        binding.rlyProgress.ctrLoader.visibility = View.VISIBLE
    }

    private fun hideListLoader() {
        binding.rlyProgress.ctrLoader.visibility = View.GONE
    }

    override fun onItemClicked(item: SelectableModel<QuickAccessForSelection>) {
        viewModel.addSelectedProject {
            adapter.unselectItem(item)
        }
    }
}