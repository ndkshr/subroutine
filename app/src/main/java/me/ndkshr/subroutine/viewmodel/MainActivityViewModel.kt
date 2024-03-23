package me.ndkshr.subroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.ndkshr.subroutine.modal.local.AppDatabaseHelper
import me.ndkshr.subroutine.modal.DailyTaskDataItem
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.modal.HabitViewItem
import me.ndkshr.subroutine.modal.IRepository
import me.ndkshr.subroutine.modal.local.LocalRepositoryImpl
import java.time.LocalDateTime
import java.util.Calendar

class MainActivityViewModel(
    private val localRepo: IRepository,
    // private val remoteRepo: IRepository
) : ViewModel() {

    var habitsDataUpdated = MutableLiveData(false)
    var habitTasksMap = HashMap<String, HabitViewItem>()

    init {
        getAllHabits()
    }

    fun getAllHabits() = viewModelScope.launch {
        localRepo.getAllHabits().collect {
            it.forEach { habit ->
                habitTasksMap[habit.habitId] = HabitViewItem(habit, mutableListOf())
            }
            delay(1000)
            getLastWeekTasks()
        }
    }

    fun getLastWeekTasks() {
        var day = LocalDateTime.now()
        val lastWeek = mutableListOf<String>()
        for (i in 0 until 7) {
            val ts: String = day.toString().split("T")[0]
            day = day.minusDays(1)
            lastWeek.add(ts)
        }
        viewModelScope.launch {
            localRepo.getAllTasks().apply {
                this.forEach { dailyTaskDataItem ->
                    if (dailyTaskDataItem.dayTs in lastWeek) {
                        habitTasksMap[dailyTaskDataItem.habitId]?.days?.add(dailyTaskDataItem)
                    }
                }
                habitsDataUpdated.postValue(true)
            }
        }
    }

    fun addHabit(habit: HabitDataItem) = viewModelScope.launch {
        localRepo.insertHabit(habit)
        val max = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
        var day = LocalDateTime.of(LocalDateTime.now().year, 1, 1, 0, 0)
        for (index in 1 until (max + 1)) {
            localRepo.insertDailyTask(
                DailyTaskDataItem(
                    habitId = habit.habitId,
                    dayTs = day.toString().split("T")[0],
                    dayStatus = false,
                )
            )
            day = day.plusDays(1)
        }
    }

    fun updateTask(dailyTaskDataItem: DailyTaskDataItem) = viewModelScope.launch {
        localRepo.updateDailyTask(dailyTaskDataItem)
    }

    fun delete(habit: HabitDataItem) = viewModelScope.launch {
        habitTasksMap.remove(habit.habitId)
        localRepo.deleteHabit(habit)
    }
}

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val habitDao = AppDatabaseHelper.DATABASE?.habitDao()!!
        val dailyTaskDao = AppDatabaseHelper.DATABASE?.dailyTaskDao()!!
        val localRepo = LocalRepositoryImpl(habitDao, dailyTaskDao)
        // val remoteRepo = RemoteRepositoryImpl(habitDao, dailyTaskDao)
        return MainActivityViewModel(localRepo) as T
    }
}
