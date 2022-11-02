package com.app.ichsanulalifwan.tasklist.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity
import com.app.ichsanulalifwan.tasklist.databinding.ItemTaskBinding
import com.app.ichsanulalifwan.tasklist.ui.tasklist.TaskListViewModel
import com.app.ichsanulalifwan.tasklist.utils.Utils

class TaskAdapter(
    private val context: Context,
    private val viewModel: TaskListViewModel,
    private val viewLifeCycleOwner: LifecycleOwner
) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var listTask = ArrayList<TaskEntity>()

    fun setData(tasks: List<TaskEntity>) {
        if (tasks.isNotEmpty()) {
            listTask.clear()
        }
        listTask.clear()
        listTask.addAll(tasks)
        notifyDataSetChanged()
    }

    fun clear() {
        val size = listTask.size
        if (size > 0) {
            for (i in 0 until size) {
                listTask.removeAt(0)
            }
            notifyItemRangeRemoved(0, size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskHolder(binding, viewModel, viewLifeCycleOwner)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(listTask[position])
    }

    override fun getItemCount(): Int = listTask.size

    inner class TaskHolder(
        private val binding: ItemTaskBinding,
        private val holderViewModel: TaskListViewModel,
        private val holderViewLifeCycleOwner: LifecycleOwner
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskEntity) = binding.run {
            val isDone = task.done
            val position = this@TaskHolder.absoluteAdapterPosition
            tvText.text = task.text
            tvDate.text = Utils.dateFormatter(task.date)
            checkDone.isChecked = isDone

            if (isDone) tvText.paintFlags = tvText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else tvText.paintFlags = tvText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            checkDone.setOnCheckedChangeListener { _, state ->
                if (state) {
                    if (!isDone) {
                        task.done = true
                        updateTask(task, holderViewModel, holderViewLifeCycleOwner)
                    }
                } else {
                    if (isDone) {
                        task.done = false
                        updateTask(task, holderViewModel, holderViewLifeCycleOwner)
                    }
                }
            }
        }

        private fun updateTask(
            task: TaskEntity,
            viewModel: TaskListViewModel,
            viewLifeCycleOwner: LifecycleOwner
        ) {
            viewModel.apply {
                updateTask(task)
                observableEditStatus.observe(viewLifeCycleOwner) {
                    Toast.makeText(context, "Task have been updated", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}