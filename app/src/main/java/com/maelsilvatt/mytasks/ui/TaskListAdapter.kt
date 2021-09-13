package com.maelsilvatt.mytasks.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maelsilvatt.mytasks.R
import com.maelsilvatt.mytasks.databinding.ItemTaskBinding
import com.maelsilvatt.mytasks.model.Task

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallBack())  {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task){
            binding.tvTaskTitle.text = item.title
            binding.tvTaskDate.text = "${item.hour}:${item.date}"
            binding.ivEditTask.setOnClickListener{
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val ivEditTask = binding.ivEditTask
            val popupMenu = PopupMenu(ivEditTask.context, ivEditTask)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                    R.id.action_share -> {}
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}

class DiffCallBack:DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task)= oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task)= oldItem.id == newItem.id
}