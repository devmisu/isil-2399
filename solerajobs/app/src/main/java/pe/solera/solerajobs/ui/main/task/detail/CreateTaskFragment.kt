package pe.solera.solerajobs.ui.main.task.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.*
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.ConstantsCore
import pe.solera.core.extension.showMaterialDialog
import pe.solera.core.extension.showMaterialDialogWithOptions
import pe.solera.core.extension.toDate
import pe.solera.core.extension.toDateString
import pe.solera.entity.TaskIdentifier
import pe.solera.entity.TaskType
import pe.solera.solerajobs.BuildConfig
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentCreateTaskBinding
import pe.solera.solerajobs.ui.BaseFragment
import pe.solera.solerajobs.ui.convertToHoursOfTask
import pe.solera.solerajobs.ui.getDateFromFuture
import pe.solera.solerajobs.ui.validateException

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment() {

    private lateinit var binding : FragmentCreateTaskBinding

    private val viewModel : TaskDetailViewModel by activityViewModels()

    private var alertDialog : AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_task, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClientSelector()
        setupProjectSelector()
        setupRequirementSelector()
        setupHourSelector()
        setupDateSelector()
        setupCommentInput()
        setupCreateTaskButton()
        viewModel.getListByTaskType(TaskIdentifier(TaskType.CLIENT))
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.taskDetailEventLiveData.observe(viewLifecycleOwner) {
            this.isLoading = it.isLoading
            when(it) {
                is TaskDetailEventResult.LoadingTaskIdentifier -> {
                    alertDialog?.hide()
                    alertDialog = requireActivity().showMaterialDialog(
                        message = it.message
                    )
                }
                is TaskDetailEventResult.SuccessTaskIdentifier -> {
                    alertDialog?.hide()
                    when(it.type) {
                        TaskType.CLIENT -> {
                            binding.tvClientSelector.isEnabled = true
                            binding.tvProjectSelector.text = getString(R.string.select_project)
                            binding.tvRequirementSelector.text = getString(R.string.select_requirement)
                            binding.tvRequirementSelector.isEnabled = false
                        }
                        TaskType.PROJECT -> {
                            binding.tvProjectSelector.isEnabled = true
                            binding.tvProjectSelector.text = getString(R.string.select_project)
                            binding.tvRequirementSelector.isEnabled = false
                            binding.tvRequirementSelector.text = getString(R.string.select_requirement)
                        }
                        TaskType.REQUIREMENT -> {
                            binding.tvRequirementSelector.isEnabled = true
                        }
                    }
                }
                TaskDetailEventResult.LoadingCreateTask -> {
                    binding.progressSave.visibility = View.VISIBLE
                }
                TaskDetailEventResult.SuccessCreateTask -> requireActivity().finish()
                is TaskDetailEventResult.Error -> requireActivity().validateException(it.ex) {
                    binding.progressSave.visibility = View.GONE
                    alertDialog?.hide()
                    alertDialog = requireActivity().showMaterialDialog(
                        message = this
                    )
                }
            }
        }
    }

    private fun setupClientSelector() {
        binding.tvClientSelector.isEnabled = false
        binding.tvClientSelector.setOnClickListener {
            alertDialog = requireActivity().showMaterialDialogWithOptions(
                itemNames = viewModel.clientsNameList(),
                title = getString(R.string.select_client),
                showNegativeButton = true,
                previousSelectedItemIndex = viewModel.userTaskModel.clientIdentifier.first
            ) { index, _, action ->
                if (action) {
                    viewModel.userTaskModel.clientIdentifier = Pair(index, viewModel.clientList[index])
                    binding.tvClientSelector.text = viewModel.userTaskModel.clientIdentifier.second.name
                    viewModel.getListByTaskType(viewModel.userTaskModel.clientIdentifier.second.copy(TaskType.PROJECT))
                }
            }
        }
    }

    private fun setupProjectSelector() {
        binding.tvProjectSelector.isEnabled = false
        binding.tvProjectSelector.setOnClickListener {
            alertDialog = requireActivity().showMaterialDialogWithOptions(
                itemNames = viewModel.projectsNameList(),
                title = getString(R.string.select_project),
                showNegativeButton = true,
                previousSelectedItemIndex = viewModel.userTaskModel.projectIdentifier.first
            ) { index, _, action ->
                if (action) {
                    viewModel.userTaskModel.projectIdentifier = Pair(index, viewModel.projectList[index])
                    binding.tvProjectSelector.text = viewModel.userTaskModel.projectIdentifier.second.name
                    viewModel.getListByTaskType(viewModel.userTaskModel.projectIdentifier.second.copy(taskType = TaskType.REQUIREMENT))
                }
            }
        }
    }

    private fun setupRequirementSelector() {
        binding.tvRequirementSelector.isEnabled = false
        binding.tvRequirementSelector.setOnClickListener {
            alertDialog = requireActivity().showMaterialDialogWithOptions(
                itemNames = viewModel.requirementsNameList(),
                title = getString(R.string.select_requirement),
                showNegativeButton = true,
                previousSelectedItemIndex = viewModel.userTaskModel.requirementIdentifier.first
            ) { index, _, action ->
                if (action) {
                    viewModel.userTaskModel.requirementIdentifier = Pair(index, viewModel.requirementList[index])
                    binding.tvRequirementSelector.text = viewModel.userTaskModel.requirementIdentifier.second.name
                }
            }
        }
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

    private fun setupDateSelector() {
        binding.tvDateSelector.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText(BuildConfig.APP_NAME)
            val startDate = getDateFromFuture(-1)
            val endDate = getDateFromFuture(90)
            val selectedDate : Long? = viewModel.userTaskModel.date?.time
            val validators: ArrayList<CalendarConstraints.DateValidator> = ArrayList()
            validators.add(DateValidatorPointForward.from(startDate))
            validators.add(DateValidatorPointBackward.before(endDate))
            val constraintsBuilder = CalendarConstraints.Builder().apply {
                setStart(startDate)
                setEnd(endDate)
                setValidator(CompositeDateValidator.allOf(validators))
                selectedDate?.let {
                    setOpenAt(it)
                }
            }.build()
            builder.setCalendarConstraints(constraintsBuilder)
            builder.setSelection(selectedDate)
            builder.build().let { picker ->
                picker.addOnPositiveButtonClickListener {
                    viewModel.userTaskModel.date = it toDate ConstantsCore.DatePattern.yyyyMMdd
                    binding.tvDateSelector.text = it toDateString ConstantsCore.DatePattern.ddMMyyyy_slashes
                }
                picker.addOnNegativeButtonClickListener {
                    picker.dismiss()
                }
                picker.show(requireActivity().supportFragmentManager, picker.toString())
            }
        }
    }

    private fun setupCommentInput() {
        binding.edtComment.setText(viewModel.userTaskModel.comment)
        binding.edtComment.addTextChangedListener {
            viewModel.userTaskModel.comment = (it ?: String()).toString().trim()
        }
    }

    private fun setupCreateTaskButton() {
        binding.btnCreate.setOnClickListener {
            if (!isLoading) {
                viewModel.validateCreation(
                    valid = {
                        viewModel.createTask()
                    },
                    errorMessage = {
                        alertDialog = requireActivity().showMaterialDialog(
                            message = it
                        )
                    }
                )
            }
        }
    }
}