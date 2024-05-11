package com.aroshana.taskmaster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aroshana.taskmaster.data.TaskDao
import com.aroshana.taskmaster.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // If the INSTANCE is not null, then return it,
            // if it is, then create the database.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_manager_db"
                )
                    .fallbackToDestructiveMigration() // This will destroy all old data on version changes
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
