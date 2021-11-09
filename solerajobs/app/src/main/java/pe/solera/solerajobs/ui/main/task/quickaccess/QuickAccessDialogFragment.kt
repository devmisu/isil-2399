package pe.solera.solerajobs.ui.main.task.quickaccess

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import pe.solera.entity.QuickAccess
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentQuickaccessBinding
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailActivity
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailViewModel
import pe.solera.solerajobs.ui.main.task.detail.TaskDetailViewModel.Companion.USER_TASK_ID

class QuickAccessDialogFragment : DialogFragment(), QuickAccessOptionsAdapter.QuickAccessOptionsListener {

    private lateinit var binding: FragmentQuickaccessBinding

    private var adapter: QuickAccessOptionsAdapter = QuickAccessOptionsAdapter()
    private var list: ArrayList<QuickAccess> = ArrayList()

    companion object {
        private const val QUICK_ACCESS_LIST = "QUICK_ACCESS_LIST"

        fun newInstance(list: ArrayList<QuickAccess>) : QuickAccessDialogFragment {
            return QuickAccessDialogFragment().apply {
                val bundle = Bundle()
                bundle.putParcelableArrayList(QUICK_ACCESS_LIST, list)
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quickaccess, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.QuickAccessDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = arguments?.getParcelableArrayList(QUICK_ACCESS_LIST) ?: ArrayList()
        adapter.items = list
        adapter.listener = this
        binding.rcvQuickAccessOptions.adapter = adapter
        binding.btnCreateTask.setOnClickListener {
            startActivity(Intent(requireActivity(), TaskDetailActivity::class.java).apply {
                putExtra(USER_TASK_ID, TaskDetailViewModel.NEW_USER_TASK_ID)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
            dismiss()
        }
    }

    override fun optionSelected(optionModel: QuickAccess) {
        startActivity(Intent(requireActivity(), TaskDetailActivity::class.java).apply {
            putExtra(USER_TASK_ID, TaskDetailViewModel.NEW_USER_TASK_ID)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
        })
        dismiss()
    }
}