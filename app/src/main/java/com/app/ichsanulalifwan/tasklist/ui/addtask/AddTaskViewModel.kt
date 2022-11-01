package com.app.ichsanulalifwan.tasklist.ui.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.ichsanulalifwan.tasklist.data.TaskRepository
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity

class AddTaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val addStatus = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = addStatus

    fun addTodo(task: TaskEntity) {
        addStatus.value = try {
            repository.insert(task)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}