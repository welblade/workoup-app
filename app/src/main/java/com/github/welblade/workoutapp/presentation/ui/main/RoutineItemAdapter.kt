package com.github.welblade.workoutapp.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.welblade.workoutapp.core.extension.format
import com.github.welblade.workoutapp.data.model.Routine
import com.github.welblade.workoutapp.databinding.ItemRoutineBinding

class RoutineItemAdapter : ListAdapter<Routine, RoutineItemAdapter.ViewHolder>(DiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoutineBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRoutineBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(routine: Routine) {
            val dayOfWeek = routine.date.toDate().format("E")
            binding.tvDayOfWeek.text = dayOfWeek
            binding.tvName.text = routine.name
            binding.tvDescription.text = routine.description
        }
    }
}

class DiffCallBack: DiffUtil.ItemCallback<Routine>() {
    override fun areItemsTheSame(oldItem: Routine, newItem: Routine): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Routine, newItem: Routine): Boolean {
        return oldItem.name == newItem.name
                && oldItem.date == newItem.date
                && oldItem.description == newItem.description
    }
}
