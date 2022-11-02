package com.app.ichsanulalifwan.tasklist.ui.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.ichsanulalifwan.tasklist.data.TaskRepository
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity

class TaskListViewModel(private val repository: TaskRepository) : ViewModel() {

    private val updateStatus = MutableLiveData<Boolean>()
    private val deleteStatus = MutableLiveData<Boolean>()

    val observableEditStatus: LiveData<Boolean>
        get() = updateStatus

    val observableDeleteStatus: LiveData<Boolean>
        get() = deleteStatus

    fun getAllTask(): LiveData<List<TaskEntity>> = repository.getTaskList()

    fun updateTask(task: TaskEntity) {
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

    fun deleteCompletedTask() {
        deleteStatus.value = try {
            repository.deleteCompletedTask()
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}