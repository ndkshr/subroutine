package me.ndkshr.subroutine.modal

import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getAllHabits(): Flow<List<HabitDataItem>>

    suspend fun getAllTasks(): List<DailyTaskDataItem>

    suspend fun getDailyTasksForHabit(habit: HabitDataItem): List<DailyTaskDataItem>

    suspend fun insertHabit(habit: HabitDataItem)

    suspend fun insertDailyTask(dailyTask: DailyTaskDataItem)

    suspend fun deleteHabit(habit: HabitDataItem)

    suspend fun updateDailyTask(dailyTask: DailyTaskDataItem)

    suspend fun deleteDailyTasksForHabit(habit: HabitDataItem)
}
