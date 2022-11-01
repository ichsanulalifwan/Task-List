package com.app.ichsanulalifwan.tasklist.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_entities")
data class TaskEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "order")
    var order: Int,

    @ColumnInfo(name = "done")
    var done: Boolean = false
)