package pe.solera.solerajobs.ui.dialog.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pe.solera.entity.Project
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.BottomSheetProjectDetailBinding
import pe.solera.solerajobs.ui.dialog.ChildBottomSheet

class ProjectDetailFragment : ChildBottomSheet() {

    private lateinit var binding : BottomSheetProjectDetailBinding

    private var adapter: RequirementsAdapter = RequirementsAdapter()

    companion object {
        fun newInstance(bundle: Bundle?) : Fragment {
            return ProjectDetailFragment().apply {
                arguments = bundle
            }
        }

        fun bundle(
            project: Project
        ) : Bundle {
            return Bundle().apply {
                putParcelable(PROJECT_SELECTED, project)
            }
        }

        private const val PROJECT_SELECTED = "PROJECT_SELECTED"
    }

    private var projectSelected: Project = Project()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_project_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectSelected = arguments?.getParcelable(PROJECT_SELECTED) ?: Project()
        binding.tvClientName.text = getString(R.string.client_name, projectSelected.clientName)
        binding.tvProjectName.text = getString(R.string.project_name, projectSelected.projectName)
        binding.tvProjectManager.text = getString(R.string.project_manager_name, projectSelected.managerName)
        adapter.items = projectSelected.requirements
        binding.rcvRequirements.adapter = adapter
    }

}