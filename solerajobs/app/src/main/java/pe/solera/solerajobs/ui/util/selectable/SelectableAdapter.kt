package pe.solera.solerajobs.ui.util.selectable

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class SelectableAdapter<T: Parcelable, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    object SelectedItems {
        const val SELECTED_ITEM : String = "SELECTED_ITEM"
        const val ORIGINAL_SOURCE : String = "ORIGINAL_SOURCE"
    }

    interface SelectableAdapterListener<Y: Parcelable> {
        fun onItemClicked(item: SelectableModel<Y>)
    }

    var items : ArrayList<SelectableModel<T>> = ArrayList()
        set(value) {
            val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(
                SelectableItemsDiffCallback(items, value)
            )
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = this.items.size

    class SelectableItemsDiffCallback<T: Parcelable>(private var oldList: ArrayList<SelectableModel<T>>, private  var newList: ArrayList<SelectableModel<T>>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].identifier == newList[newItemPosition].identifier

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].identifier == newList[newItemPosition].identifier
    }

}
