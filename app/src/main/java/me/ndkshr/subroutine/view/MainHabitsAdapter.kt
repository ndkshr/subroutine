package me.ndkshr.subroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.subroutine.databinding.HabitItemViewBinding
import me.ndkshr.subroutine.modal.HabitViewItem

class MainHabitsAdapter(
    private val checkChangeListener: HabitsViewHolder.CheckChangeListener
) : RecyclerView.Adapter<HabitsViewHolder>() {

    var list = mutableListOf<HabitViewItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        return HabitsViewHolder(
            HabitItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            checkChangeListener
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
