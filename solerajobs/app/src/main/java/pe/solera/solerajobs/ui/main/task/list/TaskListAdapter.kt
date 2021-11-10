package pe.solera.solerajobs.ui.main.task.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pe.solera.entity.UserTask
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.ItemUserTaskBinding

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var items: ArrayList<UserTask> = ArrayList()
        set(value) {
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                TaskListItemsDiffCallback(items, value)
            )
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    var listener : TaskListListener? = null

    interface TaskListListener {
        fun taskClicked(id: Int)
        fun snapLess(modified: Pair<Int, Double>)
        fun snapMore(modified: Pair<Int, Double>)
    }

    override fun getItemCount(): Int = this.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user_task,
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    fun updateRow(modified: Pair<Int, Double>) {
        items.forEachIndexed { index, userTask ->
            if (userTask.id == modified.first) {
                notifyItemChanged(index)
                return@forEachIndexed
            }
        }
    }

    inner class TaskListItemsDiffCallback(
        private var oldList: ArrayList<UserTask>,
        private var newList: ArrayList<UserTask>
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

    inner class TaskViewHolder(
        private val viewBinding: ItemUserTaskBinding,
        private val listener: TaskListListener?
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindView(userTask: UserTask) {
            viewBinding.userTask = userTask
            viewBinding.ctrTaskItem.setOnClickListener {
                listener?.taskClicked(userTask.id)
            }
            viewBinding.btnLess.setOnClickListener {
                userTask.realHours--
                listener?.snapLess(Pair(userTask.id, userTask.realHours))
            }
            viewBinding.btnMore.setOnClickListener {
                userTask.realHours++
                listener?.snapMore(Pair(userTask.id, userTask.realHours))
            }
        }

    }
}