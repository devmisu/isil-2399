package pe.solera.solerajobs.ui.main.task.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.entity.UserTask
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentTaskListBinding
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailActivity
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailViewModel
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailViewModel.Companion.USER_TASK_ID
import pe.solera.solerajobs.ui.validateException

@AndroidEntryPoint
class TaskListFragment : Fragment(), TaskListAdapter.TaskListListener {

    private lateinit var binding : FragmentTaskListBinding

    private val viewModel : TaskViewModel by viewModels()

    private lateinit var adapter : TaskListAdapter

    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDateSelected.text = viewModel.getTextualCurrentDay()
        viewModel.getUserInfoAndTasksOfDay()
        setupBackDay()
        setupNextDay()
        setupAddTaskButton()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userTaskEventLiveData.observe(viewLifecycleOwner) {
            this.isLoading = it.isLoading
            when(it) {
                is TaskListEventResult.UserInfo -> {
                    binding.tvUserName.text = "${it.user.name} ${it.user.lastName}"
                    viewModel.getUserTasksOfDay()
                }
                is TaskListEventResult.CurrentDayModified -> {
                    binding.tvDateSelected.text = it.day
                }
                is TaskListEventResult.UserTasksOfDayAndTotalHours -> {
                    setTotalHoursOfDay(it.totalHours)
                    fillUserTaskRecyclerView(it.tasks)
                    hideListLoader()
                }
                TaskListEventResult.Loading -> {
                    showListLoader()
                }
                is TaskListEventResult.Error -> requireActivity().validateException(it.ex) {
                    println(this)
                }
            }
        }
    }

    private fun setTotalHoursOfDay(total: String) {
        binding.tvTotalHoursOfDay.text = getString(R.string.total_hours, total)
    }

    private fun fillUserTaskRecyclerView(userTasks: ArrayList<UserTask>) {
        if (this.binding.rcvUserTasks.adapter == null) {
            this.adapter = TaskListAdapter()
            this.binding.rcvUserTasks.adapter = this.adapter
        }
        this.adapter.listener = this
        this.adapter.items = userTasks
    }

    private fun showListLoader() {
        binding.rlyProgress.ctrLoader.visibility = View.VISIBLE
    }

    private fun hideListLoader() {
        binding.rlyProgress.ctrLoader.visibility = View.GONE
    }

    private fun setupBackDay() {
        binding.btnBackDate.setOnClickListener {
            if (!isLoading) {
                viewModel.backDay()
            }
        }
    }

    private fun setupNextDay() {
        binding.btnNextDate.setOnClickListener {
            if (!isLoading) {
                viewModel.nextDay()
            }
        }
    }

    private fun setupAddTaskButton() {
        binding.btnCreateTask.setOnClickListener {
            startActivity(Intent(requireActivity(), TaskDetailActivity::class.java).apply {
                putExtra(USER_TASK_ID, TaskDetailViewModel.NEW_USER_TASK_ID)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
        }
    }

    override fun taskClicked(id: Int) {
        startActivity(Intent(requireActivity(), TaskDetailActivity::class.java).apply {
            putExtra(USER_TASK_ID, id)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
        })
    }
}
