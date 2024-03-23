package me.ndkshr.subroutine.modal.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.ndkshr.subroutine.modal.local.DailyTaskDao
import me.ndkshr.subroutine.modal.DailyTaskDataItem
import me.ndkshr.subroutine.modal.local.HabitDao
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.modal.IRepository

class LocalRepositoryImpl(
    private val habitDao: HabitDao,
    private val dailyTaskDao: DailyTaskDao
) : IRepository {
    override suspend fun getAllHabits(): Flow<List<HabitDataItem>> =
        withContext(Dispatchers.IO) {
            habitDao.getAll()
        }

    override suspend fun getAllTasks(): List<DailyTaskDataItem> = withContext(Dispatchers.IO){
        dailyTaskDao.getAll()
    }

    override suspend fun getDailyTasksForHabit(habit: HabitDataItem): List<DailyTaskDataItem> =
        withContext(Dispatchers.IO) {
            dailyTaskDao.getAllByHabit(habit.habitId)
        }

    override suspend fun insertHabit(habit: HabitDataItem) =
        withContext(Dispatchers.IO) {
            habitDao.insertAll(habit)
        }

    override suspend fun insertDailyTask(dailyTask: DailyTaskDataItem) =
        withContext(Dispatchers.IO) {
            dailyTaskDao.insertAll(dailyTask)
        }

    override suspend fun deleteHabit(habit: HabitDataItem) =
        withContext(Dispatchers.IO) {
            deleteDailyTasksForHabit(habit)
            habitDao.delete(habit)
        }

    override suspend fun updateDailyTask(dailyTask: DailyTaskDataItem) =
        withContext(Dispatchers.IO) {
            dailyTaskDao.updateDailyTask(dailyTask.habitId, dailyTask.dayTs, dailyTask.dayStatus)
        }

    override suspend fun deleteDailyTasksForHabit(habit: HabitDataItem) =
        withContext(Dispatchers.IO) {
            dailyTaskDao.deleteHabitTasks(habit.habitId)
        }

}
