package pe.solera.solerajobs.ui.dialog.chart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pe.solera.entity.Requirement
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ItemRequirementBinding
import pe.solera.solerajobs.ui.convertToHoursOfTask

class RequirementsAdapter : RecyclerView.Adapter<RequirementsAdapter.RequirementViewHolder>(){

    var items: ArrayList<Requirement> = ArrayList()
        set(value) {
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                RequirementDiffCallback(items, value)
            )
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequirementViewHolder {
        return RequirementViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_requirement,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RequirementViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = this.items.size

    inner class RequirementDiffCallback(
        private var oldList: ArrayList<Requirement>,
        private var newList: ArrayList<Requirement>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    inner class RequirementViewHolder(
        private val viewBinding: ItemRequirementBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindView(requirement: Requirement) {
            viewBinding.tvTitle.text = requirement.description
            viewBinding.tvHours.text = convertToHoursOfTask(requirement.realHours)
        }

    }
}