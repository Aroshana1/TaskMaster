package com.aroshana.taskmaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroshana.taskmaster.databinding.ItemTaskBinding
import com.aroshana.taskmaster.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(
    private val onTaskClicked: (Task) -> Unit,
    private val onTaskLongClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks: List<Task> = emptyList()

    // ViewHolder class for task items
    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, onTaskClicked: (Task) -> Unit, onTaskLongClicked: (Task) -> Unit) {
            binding.tvTaskName.text = task.name
            binding.tvTaskDescription.text = task.description

            // Format and display the deadline date
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.tvDeadline.text = sdf.format(task.deadline)

            // Set click listeners
            binding.root.setOnClickListener {
                onTaskClicked(task)
            }
            binding.root.setOnLongClickListener {
                onTaskLongClicked(task)
                true // Indicates that the callback consumed the long click
            }
        }
    }

    // Creates new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    // Replaces the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], onTaskClicked, onTaskLongClicked)
    }

    // Returns the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = tasks.size

    // Updates the list of tasks and notifies the adapter to refresh
    fun setTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
