package pe.solera.solerajobs.ui.main.task.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.extension.addFragment
import pe.solera.core.extension.showMaterialDialog
import pe.solera.core.extension.showMaterialDialogWithOptions
import pe.solera.entity.UserTask
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ActivityTaskDetailBinding
import pe.solera.solerajobs.ui.BaseActivity
import pe.solera.solerajobs.ui.convertToHoursOfTask
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
        setContentView(binding.root)
        binding.tvTitle.text = getString(R.string.edit_task_title)
        setFragmentToBeShown()
        setBackButton()
    }

    private fun setFragmentToBeShown() {
        if (viewModel.isNewTask) {

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
