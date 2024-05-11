package com.aroshana.taskmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var description: String,
    var priority: Int,
    var deadline: Long
)
