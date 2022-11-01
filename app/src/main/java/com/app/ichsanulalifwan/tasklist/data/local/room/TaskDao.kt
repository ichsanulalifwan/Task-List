package com.app.ichsanulalifwan.tasklist.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.ichsanulalifwan.tasklist.data.entity.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_entities")
    fun getALlTask(): LiveData<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Update
    suspend fun updateAllTask(task: List<TaskEntity>)

//    @Query("Delete FROM task_entities where done = 1")
//    fun deleteDoneTask(): LiveData<List<TaskEntity>>
}