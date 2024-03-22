package me.ndkshr.subroutine.modal

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

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
