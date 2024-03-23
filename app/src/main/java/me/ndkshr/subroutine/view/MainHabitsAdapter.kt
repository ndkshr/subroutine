package me.ndkshr.subroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.subroutine.databinding.HabitItemViewBinding
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.viewmodel.MainActivityViewModel

class MainHabitsAdapter(
    private val interactionListener: HabitsViewHolder.InteractionListener,
    private val mainViewModel: MainActivityViewModel
) : RecyclerView.Adapter<HabitsViewHolder>() {

    var list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        return HabitsViewHolder(
            HabitItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interactionListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        val habitId = list[position]
        val habit = mainViewModel.habitTasksMap[habitId]?.habitData!!
        val tasks = mainViewModel.habitTasksMap[habitId]?.days ?: mutableListOf()
        holder.bind(HabitViewItem(habit, tasks))
    }
}
