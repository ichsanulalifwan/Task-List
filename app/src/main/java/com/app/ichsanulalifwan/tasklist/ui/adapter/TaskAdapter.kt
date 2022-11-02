package com.app.ichsanulalifwan.tasklist.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity
import com.app.ichsanulalifwan.tasklist.databinding.ItemTaskBinding
import com.app.ichsanulalifwan.tasklist.utils.Utils

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.Holder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    private var listTask = ArrayList<TaskEntity>()

    fun setData(tasks: List<TaskEntity>) {
        if (tasks.isNotEmpty()) {
            listTask.clear()
        }
        listTask.addAll(tasks)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
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

            if (task.done) {
                tvText.paintFlags = tvText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(task)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TaskEntity)
    }
}