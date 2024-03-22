package me.ndkshr.subroutine.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.ndkshr.subroutine.modal.AppDatabaseHelper
import me.ndkshr.subroutine.modal.DailyTaskDataItem
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.modal.IRepository
import me.ndkshr.subroutine.modal.local.LocalRepositoryImpl
import me.ndkshr.subroutine.modal.remote.RemoteRepositoryImpl
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.time.Duration.Companion.days

class MainActivityViewModel(
    private val localRepo: IRepository,
    // private val remoteRepo: IRepository
) : ViewModel() {

    private val _habits = MutableLiveData<List<HabitDataItem>>()
    val habits: LiveData<List<HabitDataItem>> = _habits

    var habitsDataUpdated = MutableLiveData(false)
    var habitTasksMap = HashMap<String, List<DailyTaskDataItem>>()

    init {
        getAllHabits()
    }

    fun getAllHabits() = viewModelScope.launch {
        localRepo.getAllHabits().collect {
            _habits.postValue(it)
        }
    }

    fun getLastWeekTasks(habit: HabitDataItem) {
        val tasks = mutableListOf<DailyTaskDataItem>()
        var day = LocalDateTime.now()
        viewModelScope.launch {
            localRepo.getDailyTasksForHabit(habit).apply {
                for (i in 0 until 7) {
                    val ts = day.toString().split("T")[0]
                    day = day.minusDays(1)
                    tasks.add(this.find { taskItem -> (taskItem.dayTs == ts) } ?: continue)
                }
                habitTasksMap[habit.habitId] = tasks.reversed()
                habitsDataUpdated.postValue(true)
            }
        }
    }

    fun addHabit(habit: HabitDataItem) = viewModelScope.launch {
        localRepo.insertHabit(habit)
        val max = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
        var day = LocalDateTime.of(LocalDateTime.now().year, 1,1, 0, 0)
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
