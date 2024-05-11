package com.aroshana.taskmaster.data

import androidx.room.*
import com.aroshana.taskmaster.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Task Data Access Object (DAO) interface
 */
@Dao
interface TaskDao {

    /**
     * Retrieves all tasks ordered by priority (descending) and deadline (ascending)
     */
    @Query("SELECT * FROM tasks ORDER BY priority DESC, deadline ASC")
    fun getAllTasks(): Flow<List<Task>>

    /**
     * Inserts a new task into the database
     */
    @Insert
    suspend fun insertTask(task: Task)

    /**
     * Updates an existing task in the database
     */
    @Update
    suspend fun updateTask(task: Task)

    /**
     * Deletes a task from the database
     */
    @Delete
    suspend fun deleteTask(task: Task)

    /**
     * Retrieves a task by its ID
     */
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): Flow<Task?>

    /**
     * Deletes all tasks from the database
     */
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}