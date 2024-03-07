package me.ndkshr.subroutine.modal

data class HabitViewItem (
    val habitInfo: HabitDataItem,
    val days: List<TaskCheckViewItem>
)

data class TaskCheckViewItem (
    var timestamp: Long,
    var checked: Boolean
)