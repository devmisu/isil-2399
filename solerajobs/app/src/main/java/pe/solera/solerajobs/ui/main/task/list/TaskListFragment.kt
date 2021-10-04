package pe.solera.solerajobs.ui.main.task.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentTaskListBinding
import pe.solera.solerajobs.ui.validateException
import java.util.*

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var binding : FragmentTaskListBinding

    private val viewModel : TaskViewModel by viewModels()

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
        viewModel.getUserTasksOfDay(Date())
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userTaskEventLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is TaskListEventResult.UserTasksOfDay -> {
                    println("LLEGARON LAS TAREAS")
                }
                is TaskListEventResult.Error -> requireActivity().validateException(it.ex) {
                    println(this)
                }
            }
        }
    }

}