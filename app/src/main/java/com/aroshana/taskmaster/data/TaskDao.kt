package com.aroshana.taskmaster.data

import androidx.room.*
import com.aroshana.taskmaster.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {


    @Query("SELECT * FROM tasks ORDER BY priority DESC, deadline ASC")
    fun getAllTasks(): Flow<List<Task>>


    @Insert
    suspend fun insertTask(task: Task)


    @Update
    suspend fun updateTask(task: Task)


    @Delete
    suspend fun deleteTask(task: Task)


    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): Flow<Task?>


    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}