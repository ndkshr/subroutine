package me.ndkshr.subroutine.modal.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ndkshr.subroutine.modal.DailyTaskDataItem
import me.ndkshr.subroutine.modal.HabitDataItem

@Database(
    entities = [
        HabitDataItem::class,
        DailyTaskDataItem::class
    ],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun dailyTaskDao(): DailyTaskDao
}
