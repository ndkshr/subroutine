package me.ndkshr.subroutine.modal

data class HabitViewItem (
    val habitData: HabitDataItem,
    var days: MutableList<DailyTaskDataItem>
)