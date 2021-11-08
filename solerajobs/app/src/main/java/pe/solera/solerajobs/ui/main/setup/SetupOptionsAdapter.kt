package pe.solera.solerajobs.ui.main.setup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ItemSetupOptionBinding

class SetupOptionsAdapter : RecyclerView.Adapter<SetupOptionsAdapter.SetupOptionViewHolder>() {

    var items: ArrayList<SetupOptionModel> = ArrayList()
        set(value) {
            field = ArrayList()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetupOptionViewHolder {
        return SetupOptionViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.item_setup_option, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SetupOptionViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class SetupOptionViewHolder(val binding: ItemSetupOptionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(model: SetupOptionModel) {

        }

    }

}