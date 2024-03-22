package me.ndkshr.subroutine.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "habits",
    primaryKeys = ["habit_id"]
)
data class HabitDataItem(
    @ColumnInfo("habit_id")
    val habitId: String,
    @ColumnInfo(name = "habit_name")
    val habitName: String,
    @ColumnInfo("habit_label")
    val habitLabel: String,
    @ColumnInfo("habit_reminder_ts")
    val habitReminderTs: String
)
