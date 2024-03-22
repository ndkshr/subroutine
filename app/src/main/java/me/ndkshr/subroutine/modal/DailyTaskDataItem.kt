package me.ndkshr.subroutine.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "dailytasks",
    primaryKeys = ["habit_id", "day_time_stamp"],
)
data class DailyTaskDataItem (
    @ColumnInfo("habit_id")
    val habitId: String,
    @ColumnInfo("day_time_stamp")
    val dayTs: String,
    @ColumnInfo("day_status")
    var dayStatus: Boolean
)
