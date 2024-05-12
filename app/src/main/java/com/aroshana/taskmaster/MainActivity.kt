package com.aroshana.taskmaster

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroshana.taskmaster.adapter.TaskAdapter
import com.aroshana.taskmaster.database.AppDatabase
import com.aroshana.taskmaster.databinding.ActivityMainBinding
import androidx.recyclerview.widget.RecyclerView

import com.aroshana.taskmaster.model.Task
import com.aroshana.taskmaster.repository.TaskRepository
import com.aroshana.taskmaster.taskviewmodel.TaskViewModel
import com.aroshana.taskmaster.taskviewmodel.TaskViewModelFactory
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(AppDatabase.getInstance(application).taskDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeTasks()

        binding.btnAddTask.setOnClickListener {
            showAddTaskDialog()
        }

    }



    private fun setupRecyclerView() {
        val adapter = TaskAdapter(
            onTaskClicked = { task ->
                showUpdateTaskDialog(task)
            },
            onTaskLongClicked = { task ->
                viewModel.delete(task)
                Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
            }
        )
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = adapter
    }

    private fun observeTasks() {
        viewModel.allTasks.observe(this) { tasks ->
            (binding.recyclerViewTasks.adapter as TaskAdapter).setTasks(tasks)
        }
    }

    private fun showAddTaskDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_task)
        val etTaskName = dialog.findViewById<EditText>(R.id.etTaskName)
        val etTaskDescription = dialog.findViewById<EditText>(R.id.etTaskDescription)
        val etDeadline = dialog.findViewById<DatePicker>(R.id.etDeadline)
        val etPriority = dialog.findViewById<Spinner>(R.id.etPriority)
        val btnAdd = dialog.findViewById<Button>(R.id.add_button)
        val btnCancel = dialog.findViewById<Button>(R.id.cancel_button)

        btnAdd.setOnClickListener {
            val name = etTaskName.text.toString().trim()
            val description = etTaskDescription.text.toString().trim()
            val deadline = getDeadlineTimestamp(etDeadline)
            val priority = etPriority.selectedItemPosition

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val task = Task(0, name, description, priority, deadline)
                viewModel.insert(task)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showUpdateTaskDialog(task: Task) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_task)
        val etTaskName = dialog.findViewById<EditText>(R.id.etTaskName)
        val etTaskDescription = dialog.findViewById<EditText>(R.id.etTaskDescription)
        val etDeadline = dialog.findViewById<DatePicker>(R.id.etDeadline)
        val etPriority = dialog.findViewById<Spinner>(R.id.etPriority)

        etTaskName.setText(task.name)
        etTaskDescription.setText(task.description)
        loadDatePicker(etDeadline, task.deadline)
        etPriority.setSelection(task.priority)

        val btnUpdate = dialog.findViewById<Button>(R.id.btnUpdate)
        btnUpdate.text = "Update"
        btnUpdate.setOnClickListener {
            task.name = etTaskName.text.toString().trim()
            task.description = etTaskDescription.text.toString().trim()
            task.deadline = getDeadlineTimestamp(etDeadline)
            task.priority = etPriority.selectedItemPosition

            viewModel.update(task)
            dialog.dismiss()
        }

        val btnCancel = dialog.findViewById<Button>(R.id.cancel_button)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getDeadlineTimestamp(datePicker: DatePicker): Long {
        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        return calendar.timeInMillis
    }

    private fun loadDatePicker(datePicker: DatePicker, timestamp: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }



}
