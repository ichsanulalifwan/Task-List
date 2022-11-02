package com.app.ichsanulalifwan.tasklist.data

import androidx.lifecycle.LiveData
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity
import com.app.ichsanulalifwan.tasklist.data.local.room.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskRepository(private val taskDao: TaskDao) {

    fun getTaskList(): LiveData<List<TaskEntity>> {
        return taskDao.getALlTask()
    }

    fun insert(task: TaskEntity) = runBlocking {
        this.launch(Dispatchers.IO) {
            taskDao.insertTask(task)
        }
    }

    fun update(task: TaskEntity) = runBlocking {
        this.launch(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }

    fun updateAll(taskList: List<TaskEntity>) = runBlocking {
        this.launch(Dispatchers.IO) {
            taskDao.updateAllTask(taskList)
        }
    }

//    fun delete(task: TaskEntity) {
//        runBlocking {
//            this.launch(Dispatchers.IO) {
//                taskDao.deleteTask(task)
//            }
//        }
//    }

    companion object {
        @Volatile
        private var instance: TaskRepository? = null

        fun getInstance(
            taskDao: TaskDao
        ): TaskRepository =
            instance ?: synchronized(this) {
                instance ?: TaskRepository(
                    taskDao
                ).apply { instance = this }
            }
    }
}