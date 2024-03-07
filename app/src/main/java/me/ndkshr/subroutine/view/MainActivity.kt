package me.ndkshr.subroutine.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.ActivityMainBinding
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.modal.TaskCheckViewItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), HabitsViewHolder.CheckChangeListener {

    private lateinit var binding: ActivityMainBinding
    private val habitsAdapter: MainHabitsAdapter by lazy { MainHabitsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initUI()
        setDateView()
    }

    private fun initUI() {
        binding.habitsRv.layoutManager = LinearLayoutManager(this)
        binding.habitsRv.adapter = habitsAdapter
        habitsAdapter.list = mutableListOf(
            HabitViewItem(
                habitInfo = HabitDataItem(
                    100,
                    "Habit Name",
                    "#222222",
                    "Test"
                ),
                days = mutableListOf(
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = false),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = false),
                )
            ),
            HabitViewItem(
                habitInfo = HabitDataItem(
                    100,
                    "Habit Name",
                    "#222222",
                    "Test"
                ),
                days = mutableListOf(
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = false),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = true),
                    TaskCheckViewItem(timestamp = 0L, checked = false),
                )
            )
        )

        binding.addButton.setOnClickListener {
            AddHabitBottomSheetFragment().show(supportFragmentManager, AddHabitBottomSheetFragment::class.simpleName)
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

    override fun changed() {
//         habitsAdapter.notifyDataSetChanged()
        // habitsViewModel.syncDataLocal()
    }
}