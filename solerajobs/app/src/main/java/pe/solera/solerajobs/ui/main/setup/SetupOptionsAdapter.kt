package pe.solera.solerajobs.ui.main.setup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

    var listener: SetupOptionsListener? = null

    interface SetupOptionsListener {
        fun optionSelected(optionModel: SetupOptionModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetupOptionViewHolder {
        return SetupOptionViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.item_setup_option, parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: SetupOptionViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class SetupOptionViewHolder(
        val binding: ItemSetupOptionBinding,
        val listener: SetupOptionsListener?
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(model: SetupOptionModel) {
            binding.tvOptionTitle.text = model.title
            binding.tvOptionDescription.text = model.description
            binding.imgIcon.setImageDrawable(ContextCompat.getDrawable(binding.root.context, model.imgResource))
            binding.ctrSetupOption.setOnClickListener {
                listener?.optionSelected(model)
            }
        }
    }

}