package com.app.ichsanulalifwan.tasklist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_entities")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "date")
    var date: Long,

    @ColumnInfo(name = "order")
    var order: Int = 0,

    @ColumnInfo(name = "done")
    var done: Boolean = false
)