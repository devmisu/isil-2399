package pe.solera.solerajobs.ui.main.setup.quickaccess

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.solera.entity.Project
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ItemProjectPreferenceBinding
import pe.solera.solerajobs.ui.util.selectable.SelectableAdapter
import pe.solera.solerajobs.ui.util.selectable.SelectableModel

class QuickAccessAdapter(
    private val listener: SelectableAdapterListener<Project>
) : SelectableAdapter<Project, QuickAccessAdapter.QuickAccessViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickAccessViewHolder {
        return QuickAccessViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_project_preference,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuickAccessViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun unselectItem(item: SelectableModel<Project>) {
        items.forEachIndexed { index, selectableModel ->
            if (selectableModel.identifier == item.identifier) {
                selectableModel.isSelected = false
                notifyItemChanged(index)
            }
        }
    }

    class QuickAccessViewHolder(
        val binding: ItemProjectPreferenceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: SelectableModel<Project>,
            listener: SelectableAdapterListener<Project>
        ) {
            binding.isSelected = item.isSelected
            binding.name = item.model?.title ?: String()
            binding.cbxEnabled.setOnCheckedChangeListener { _, isChecked ->
                item.isSelected = isChecked
                listener.onItemClicked(item)
            }
        }

    }

}