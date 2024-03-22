package me.ndkshr.subroutine.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.HabitItemViewBinding
import me.ndkshr.subroutine.modal.DailyTaskDataItem
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.utlis.gone

class HabitsViewHolder(
    private val binding: HabitItemViewBinding,
    private val onInteractionListener: InteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(habit: HabitViewItem) {
        binding.apply {
            habitName.text = habit.habitData.habitName
            var i = 0

            if (habit.days.isEmpty()) {
                day1.gone()
                day2.gone()
                day3.gone()
                day4.gone()
                day5.gone()
                day6.gone()
                day7Bg.gone()
                return@apply
            }

            day1.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day1.setOnClickListener {
                onChanged(it as ImageView, habit.days[0], habit)
            }

            day2.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day2.setOnClickListener {
                onChanged(it as ImageView, habit.days[1], habit)
            }

            day3.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day3.setOnClickListener {
                onChanged(it as ImageView, habit.days[2], habit)
            }

            day4.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day4.setOnClickListener {
                onChanged(it as ImageView, habit.days[3], habit)
            }

            day5.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day5.setOnClickListener {
                onChanged(it as ImageView, habit.days[4], habit)
            }

            day6.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day6.setOnClickListener {
                onChanged(it as ImageView, habit.days[5], habit)
            }

            day7.setImageDrawable(getRadioDrawable(habit.days[i]))
            day7.setOnClickListener {
                onChanged(it as ImageView, habit.days[6], habit)
            }
        }

        binding.root.setOnLongClickListener {
            onInteractionListener.longClickMenu(habit)
            false
        }
    }

    private fun getRadioDrawable(dailyTaskItem: DailyTaskDataItem): Drawable {
        return if (dailyTaskItem.dayStatus) {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.square_check)!!
        } else {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.square)!!
        }
    }

    private fun getRadioDrawableWithAnimation(dailyTaskItem: DailyTaskDataItem): Drawable {
        return if (dailyTaskItem.dayStatus) {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.square_check)!!
        } else {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.square)!!
        }
    }

    private fun onChanged(view: ImageView, dailyTaskItem: DailyTaskDataItem, habit: HabitViewItem) {
        dailyTaskItem.dayStatus = dailyTaskItem.dayStatus.not()
        onInteractionListener.checkChanged(dailyTaskItem)
        view.setImageDrawable(getRadioDrawableWithAnimation(dailyTaskItem))
    }

    interface InteractionListener {
        fun checkChanged(dailyTaskItem: DailyTaskDataItem)
        fun longClickMenu(habit: HabitViewItem)
    }
}
