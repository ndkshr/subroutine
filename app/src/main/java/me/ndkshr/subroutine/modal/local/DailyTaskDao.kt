package me.ndkshr.subroutine.modal.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.ndkshr.subroutine.modal.DailyTaskDataItem

@Dao
interface DailyTaskDao {
    @Query("SELECT * FROM dailytasks")
    fun getAll(): List<DailyTaskDataItem>

    @Query("SELECT * FROM dailytasks WHERE day_time_stamp = :dayTs")
    fun getAllByDay(dayTs: String): List<DailyTaskDataItem>

    @Query("SELECT * FROM dailytasks WHERE habit_id = :habitId")
    fun getAllByHabit(habitId: String): List<DailyTaskDataItem>

    @Query("UPDATE dailytasks SET day_status = :newValue WHERE habit_id = :habitId AND day_time_stamp = :dayTs")
    fun updateDailyTask(habitId: String, dayTs: String, newValue: Boolean)

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAll(vararg dailyTasks: DailyTaskDataItem)

    @Query("DELETE FROM dailytasks WHERE habit_id = :habitId")
    fun deleteHabitTasks(habitId: String)

    @Delete
    fun delete(dailyTask: DailyTaskDataItem)
}
