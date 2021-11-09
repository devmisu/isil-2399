package pe.solera.solerajobs.ui.main.setup.quickaccess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import pe.solera.entity.Project
import pe.solera.solerajobs.databinding.ActivityQuickAccessBinding
import pe.solera.solerajobs.ui.util.selectable.SelectableAdapter
import pe.solera.solerajobs.ui.util.selectable.SelectableModel

class QuickAccessActivity : AppCompatActivity(), SelectableAdapter.SelectableAdapterListener<Project> {

    private lateinit var binding: ActivityQuickAccessBinding

    private val viewModel: QuickAccessViewModel by viewModels()

    private lateinit var adapter: QuickAccessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuickAccessBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        adapter = QuickAccessAdapter(this)
        adapter.items = viewModel.projectsToSelect
        binding.rcvProject.adapter = adapter
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onItemClicked(item: SelectableModel<Project>) {
        viewModel.addSelectedProject {
            adapter.unselectItem(item)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}