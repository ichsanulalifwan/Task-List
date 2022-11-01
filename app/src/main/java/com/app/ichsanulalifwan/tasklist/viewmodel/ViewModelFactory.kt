package com.app.ichsanulalifwan.tasklist.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.ichsanulalifwan.tasklist.data.TaskRepository
import com.app.ichsanulalifwan.tasklist.di.Injection
import com.app.ichsanulalifwan.tasklist.ui.addtask.AddTaskViewModel
import com.app.ichsanulalifwan.tasklist.ui.tasklist.TaskListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskListViewModel::class.java) -> {
                TaskListViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AddTaskViewModel::class.java) -> {
                AddTaskViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }
}