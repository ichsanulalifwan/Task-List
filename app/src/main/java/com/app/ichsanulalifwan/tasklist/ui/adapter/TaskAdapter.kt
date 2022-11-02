package com.app.ichsanulalifwan.tasklist.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity
import com.app.ichsanulalifwan.tasklist.databinding.ItemTaskBinding
import com.app.ichsanulalifwan.tasklist.utils.Utils

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.Holder>() {

    private var listTask = ArrayList<TaskEntity>()

    fun setData(tasks: List<TaskEntity>) {
        if (tasks.isNotEmpty()) {
            listTask.clear()
        }
        listTask.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listTask[position])
    }

    override fun getItemCount(): Int = listTask.size

    inner class Holder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskEntity) = binding.run {
            tvText.text = task.text
            tvDate.text = Utils.dateFormatter(task.date)
            checkDone.isChecked = task.done

//            checkDone.setOnCheckedChangeListener()

            if (task.done) {
                tvText.paintFlags = tvText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }
}