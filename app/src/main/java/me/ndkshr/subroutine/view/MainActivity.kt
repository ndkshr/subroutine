package me.ndkshr.subroutine.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.ActivityMainBinding
import me.ndkshr.subroutine.modal.DailyTaskDataItem
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.viewmodel.MainActivityViewModel
import me.ndkshr.subroutine.viewmodel.MainActivityViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), HabitsViewHolder.InteractionListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels() { MainActivityViewModelFactory() }
    private val habitsAdapter: MainHabitsAdapter by lazy { MainHabitsAdapter(this, viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setDateView()
        binding.addButton.setOnClickListener {
            AddHabitBottomSheetFragment().show(supportFragmentManager, AddHabitBottomSheetFragment::class.simpleName)
        }

        viewModel.habits.observe(this) {
            habitsAdapter.list.clear()
            it.forEach { habit ->
                habitsAdapter.list.add(habit)
                viewModel.getLastWeekTasks(habit)
            }
            Handler(mainLooper).postDelayed({
                initHabitsRv()
            }, 1000)
        }
    }

    private fun initHabitsRv() {
        binding.habitsRv.layoutManager = LinearLayoutManager(this)
        binding.habitsRv.adapter = habitsAdapter

        viewModel.habitsDataUpdated.observe(this) {
            habitsAdapter.list = viewModel.habits.value?.toMutableList() ?: mutableListOf()
            habitsAdapter.notifyDataSetChanged()
        }
    }

    private fun setDateView() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val pattern = "EEE\ndd"

        binding.apply {
            day7Text.text = SimpleDateFormat(pattern, Locale.US).format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            day6Text.text = SimpleDateFormat(pattern, Locale.US).format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            day5Text.text = SimpleDateFormat(pattern, Locale.US).format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            day4Text.text = SimpleDateFormat(pattern, Locale.US).format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            day3Text.text = SimpleDateFormat(pattern, Locale.US).format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            day2Text.text = SimpleDateFormat(pattern, Locale.US).format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            day1Text.text = SimpleDateFormat(pattern, Locale.US).format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }
    }

    override fun checkChanged(dailyTaskItem: DailyTaskDataItem) {
        Log.d("Main Activity", "Click registered")
        viewModel.updateTask(dailyTaskItem)
    }

    override fun longClickMenu(habit: HabitViewItem) {
        HabitMenuBottomSheet(habit).show(this.supportFragmentManager, "HabitMenuBottomSheet")
    }
}