package me.ndkshr.subroutine.modal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<HabitDataItem>>

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertAll(vararg habits: HabitDataItem)

    @Delete
    fun delete(habit: HabitDataItem)
}
