package pe.solera.solerajobs.ui.main.task.detail

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.extension.addFragment
import pe.solera.entity.QuickAccess
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ActivityTaskDetailBinding
import pe.solera.solerajobs.ui.BaseActivity
import pe.solera.solerajobs.ui.main.task.detail.CreateTaskFragment.Companion.QUICKACCESS
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailViewModel.Companion.NEW_USER_TASK_ID
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailViewModel.Companion.USER_TASK_ID

@AndroidEntryPoint
class TaskDetailActivity : BaseActivity() {

    private lateinit var binding : ActivityTaskDetailBinding

    private val viewModel : TaskDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        viewModel.userTask.id = intent.getIntExtra(USER_TASK_ID, NEW_USER_TASK_ID)
        viewModel.isNewTask = viewModel.userTask.id == NEW_USER_TASK_ID
        intent.getParcelableExtra<QuickAccess>(QUICKACCESS)?.let {
            viewModel.fillWithQuickAccess(it)
        }
        setContentView(binding.root)
        binding.tvTitle.text = getString(if (viewModel.isNewTask) R.string.create_task_title else R.string.edit_task_title)
        setFragmentToBeShown()
        setBackButton()
    }

    private fun setFragmentToBeShown() {
        if (viewModel.isNewTask) {
            this.supportFragmentManager.addFragment(CreateTaskFragment(), CreateTaskFragment::class.java.name, R.id.frameLytTaskDetailContainer)
        } else {
            this.supportFragmentManager.addFragment(EditTaskFragment(), EditTaskFragment::class.java.name, R.id.frameLytTaskDetailContainer)
        }
    }

    private fun setBackButton() {
        binding.btnBack.setOnClickListener {
            if (!isLoading) {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        if (!isLoading) {
            super.onBackPressed()
            if (supportFragmentManager.fragments.isEmpty()) finish()
        }
    }
}
