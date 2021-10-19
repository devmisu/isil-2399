package pe.solera.solerajobs.ui.main.task.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.extension.showMaterialDialog
import pe.solera.core.extension.showMaterialDialogWithOptions
import pe.solera.entity.UserTask
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentEditTaskBinding
import pe.solera.solerajobs.ui.BaseFragment
import pe.solera.solerajobs.ui.convertToHoursOfTask
import pe.solera.solerajobs.ui.validateException

@AndroidEntryPoint
class EditTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentEditTaskBinding

    private val viewModel : TaskDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_task, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTaskDetail()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.taskDetailEventLiveData.observe(viewLifecycleOwner) {
            this.isLoading = it.isLoading
            when(it) {
                is TaskDetailEventResult.SuccessTaskDetail -> {
                    setupUserTaskInfo(it.userTask)
                    loaderVisibility(View.GONE)
                    setupProjectSelector(it.userTask)
                    setupHourSelector()
                    setupCommentInput()
                    setupCreateOrEditButton()
                    setupDeleteButton()
                }
                is TaskDetailEventResult.Error -> requireActivity().validateException(it.ex) {
                    binding.progressSave.visibility = View.GONE
                    requireActivity().showMaterialDialog(
                        message = this
                    )
                }
                TaskDetailEventResult.LoadingEdition -> {
                    binding.progressSave.visibility = View.VISIBLE
                }
                TaskDetailEventResult.LoadingDelete -> {
                    binding.progressDelete.visibility = View.VISIBLE
                }
                TaskDetailEventResult.SuccessEdition -> {
                    binding.progressSave.visibility = View.GONE
                    binding.progressDelete.visibility = View.GONE
                    requireActivity().finish()
                }
            }
        }
    }

    private fun loaderVisibility(visible: Int) {
        binding.rlyProgress.ctrLoader.visibility = visible
    }

    private fun setupUserTaskInfo(userTask: UserTask) {
        binding.tvStartDate.text = getString(R.string.start_date, userTask.estimateStartDate)
        binding.tvEndDate.text = getString(R.string.end_date, userTask.estimateEndDate)
        binding.tvClientName.text = getString(R.string.client_name, userTask.clientName)
        binding.tvProjectName.text = getString(R.string.project_name, userTask.projectName)
        binding.tvProjectManager.text = getString(R.string.project_manager_name, userTask.projectManager)
    }

    private fun setupProjectSelector(userTask: UserTask) {
        binding.tvProjectSelector.text = userTask.projectName
    }

    private fun setupHourSelector() {
        binding.tvHourSelector.text = convertToHoursOfTask(viewModel.userTaskModel.hourSelected.second)
        binding.tvHourSelector.setOnClickListener {
            requireActivity().showMaterialDialogWithOptions(
                itemNames = viewModel.hoursNameList,
                title = "Seleccione las horas trabajadas",
                showNegativeButton = true,
                previousSelectedItemIndex = viewModel.userTaskModel.hourSelected.first
            ) { index, _, action ->
                if (action) {
                    this.viewModel.userTaskModel.hourSelected = Pair(index, viewModel.hours[index])
                    binding.tvHourSelector.text = convertToHoursOfTask(viewModel.userTaskModel.hourSelected.second)
                }
            }
        }
    }

    private fun setupCommentInput() {
        binding.edtComment.setText(viewModel.userTaskModel.comment)
        binding.edtComment.addTextChangedListener {
            viewModel.userTaskModel.comment = (it ?: String()).toString().trim()
        }
    }

    private fun setupCreateOrEditButton() {
        binding.btnCreateOrEdit.text = getString(R.string.edit)
        binding.btnCreateOrEdit.setOnClickListener {
            if (!isLoading) {
                viewModel.editTaskDetail()
            }
        }
    }

    private fun setupDeleteButton() {
        binding.btnDelete.setOnClickListener {
            if (!isLoading) {
                viewModel.deleteTask()
            }
        }
    }
}
