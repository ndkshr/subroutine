package me.ndkshr.subroutine.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "habits"
)
data class HabitDataItem(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("habit_uuid")
    val habitUUID: String,
    @ColumnInfo(name = "habit_name")
    val habitName: String,
    @ColumnInfo("habit_label")
    val habitLabel: String,
    @ColumnInfo("habit_reminder_ts")
    val habitReminderTs: String
) : Serializable
