package com.app.ichsanulalifwan.tasklist.di

import android.content.Context
import com.app.ichsanulalifwan.tasklist.data.TaskRepository
import com.app.ichsanulalifwan.tasklist.data.local.room.TaskDatabase

object Injection {

    fun provideRepository(context: Context): TaskRepository {

        val database = TaskDatabase.getInstance(context)

        return TaskRepository.getInstance(database.taskDao())
    }
}