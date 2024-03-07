package me.ndkshr.subroutine.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.HabitItemViewBinding
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.modal.TaskCheckViewItem

class HabitsViewHolder(
    private val binding: HabitItemViewBinding,
    private val onCheckChangeListener: CheckChangeListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(habit: HabitViewItem) {
        binding.apply {
            habitName.text = habit.habitInfo.habitName
            var i = 0
            day1.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day1.setOnClickListener {
                onChanged(it as ImageView, habit.days[1])
            }

            day2.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day2.setOnClickListener {
                onChanged(it as ImageView, habit.days[1])
            }

            day3.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day3.setOnClickListener {
                onChanged(it as ImageView, habit.days[2])
            }

            day4.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day4.setOnClickListener {
                onChanged(it as ImageView, habit.days[3])
            }

            day5.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day5.setOnClickListener {
                onChanged(it as ImageView, habit.days[4])
            }

            day6.setImageDrawable(getRadioDrawable(habit.days[i++]))
            day6.setOnClickListener {
                onChanged(it as ImageView, habit.days[5])
            }

            day7.setImageDrawable(getRadioDrawable(habit.days[i]))
            day7.setOnClickListener {
                onChanged(it as ImageView, habit.days[6])
            }
        }
    }

    private fun getRadioDrawable(item: TaskCheckViewItem): Drawable {
        return if (item.checked) {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.check_circle)!!
        } else {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.circle)!!
        }
    }

    private fun getRadioDrawableWithAnimation(item: TaskCheckViewItem): Drawable {
        return if (item.checked) {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.check_circle)!!
        } else {
            AppCompatResources.getDrawable(binding.root.context, R.drawable.circle)!!
        }
    }

    private fun onChanged(view: ImageView, item: TaskCheckViewItem) {
        item.checked = item.checked.not()
        onCheckChangeListener.changed()
        view.setImageDrawable(getRadioDrawableWithAnimation(item))
    }

    interface CheckChangeListener {
        fun changed()
    }
}
