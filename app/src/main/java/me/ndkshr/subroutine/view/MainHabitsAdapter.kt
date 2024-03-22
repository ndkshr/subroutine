package me.ndkshr.subroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.subroutine.databinding.HabitItemViewBinding
import me.ndkshr.subroutine.modal.DailyTaskDataItem
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.viewmodel.MainActivityViewModel
import java.time.LocalDateTime

class MainHabitsAdapter(
    private val interactionListener: HabitsViewHolder.InteractionListener,
    private val mainViewModel: MainActivityViewModel
) : RecyclerView.Adapter<HabitsViewHolder>() {

    var list = mutableListOf<HabitDataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        return HabitsViewHolder(
            HabitItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interactionListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        val habit = list[position]
        val tasks = mainViewModel.habitTasksMap[habit.habitId] ?: emptyList()
        holder.bind(HabitViewItem(habit, tasks))
    }
}
