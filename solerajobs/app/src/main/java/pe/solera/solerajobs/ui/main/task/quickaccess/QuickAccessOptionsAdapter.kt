package pe.solera.solerajobs.ui.main.task.quickaccess

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pe.solera.entity.QuickAccess
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ItemQuickaccessBinding

class QuickAccessOptionsAdapter : RecyclerView.Adapter<QuickAccessOptionsAdapter.QuickAccessOptionViewHolder>() {

    var items: ArrayList<QuickAccess> = ArrayList()
        set(value) {
            field = ArrayList()
            field = value
            notifyDataSetChanged()
        }

    var listener: QuickAccessOptionsListener? = null

    interface QuickAccessOptionsListener {
        fun optionSelected(optionModel: QuickAccess)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickAccessOptionViewHolder {
        return QuickAccessOptionViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.item_quickaccess, parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: QuickAccessOptionViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class QuickAccessOptionViewHolder(
        val binding: ItemQuickaccessBinding,
        val listener: QuickAccessOptionsListener?
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(model: QuickAccess) {
            binding.tvQuickAccessTitle.text = model.name
            binding.btnQuickAccess.setOnClickListener {
                listener?.optionSelected(model)
            }
        }
    }

}