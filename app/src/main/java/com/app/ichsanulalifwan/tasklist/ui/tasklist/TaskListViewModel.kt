package com.app.ichsanulalifwan.tasklist.ui.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.ichsanulalifwan.tasklist.data.TaskRepository
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity

class TaskListViewModel(private val repository: TaskRepository) : ViewModel() {

    private val taskList: LiveData<List<TaskEntity>>? = repository.getTaskList()
    private val updateStatus = MutableLiveData<Boolean>()

    val observableEditStatus: LiveData<Boolean>
        get() = updateStatus

    fun getAllTask(): LiveData<List<TaskEntity>>? {
        return taskList
    }

    fun updateTodo(task: TaskEntity) {
        updateStatus.value = try {
            repository.update(task)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun updateAllTask(taskList: List<TaskEntity>) {
        updateStatus.value = try {
            repository.updateAll(taskList)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}