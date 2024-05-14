package com.aroshana.taskmaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroshana.taskmaster.databinding.ItemTaskBinding
import com.aroshana.taskmaster.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(
    private val onUpdateClicked: (Task) -> Unit,
    private val onDeleteClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks: List<Task> = emptyList()


    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, onUpdateClicked: (Task) -> Unit, onDeleteClicked: (Task) -> Unit) {
            binding.tvTaskName.text = task.name
            binding.tvTaskDescription.text = task.description

            // Format and display the deadline date
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.tvDeadline.text = sdf.format(task.deadline)


            binding.btnUpdate.setOnClickListener {
                onUpdateClicked(task)
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClicked(task)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], onUpdateClicked, onDeleteClicked)
    }


    override fun getItemCount() = tasks.size


    fun setTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
