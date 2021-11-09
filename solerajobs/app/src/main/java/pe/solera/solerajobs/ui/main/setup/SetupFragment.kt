package pe.solera.solerajobs.ui.main.setup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.extension.showMaterialDialog
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentSetupBinding
import pe.solera.solerajobs.ui.BaseFragment
import pe.solera.solerajobs.ui.main.setup.quickaccess.QuickAccessActivity

@AndroidEntryPoint
class SetupFragment : BaseFragment(), SetupOptionsAdapter.SetupOptionsListener {

    private lateinit var binding: FragmentSetupBinding

    private var adapter: SetupOptionsAdapter = SetupOptionsAdapter()

    private val options : ArrayList<SetupOptionModel> = arrayListOf(
        SetupOptionModel(
            R.drawable.ic_logout,
            title = "Cerrar sesión",
            description = "Cerrar sesión",
            type = SetupOptionType.LOGOUT
        ),
        SetupOptionModel(
            R.drawable.ic_quickaccess,
            title = "Accesos directos",
            description = "Edición de preferencias",
            type = SetupOptionType.QUICK_ACCESS
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setup, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.items = options
        adapter.listener = this
        binding.rcvSetupOptions.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.rcvSetupOptions.adapter = adapter
    }

    override fun optionSelected(optionModel: SetupOptionModel) {
        when(optionModel.type) {
            SetupOptionType.LOGOUT -> {
                requireContext().showMaterialDialog(
                    message = this.getString(R.string.want_to_logout),
                    acceptBtnMsg = getString(R.string.exit),
                    showNegativeBtn = true
                ) { action ->

                }
            }
            SetupOptionType.QUICK_ACCESS -> {
                startActivity(Intent(requireActivity(), QuickAccessActivity::class.java))
            }
        }
    }
}