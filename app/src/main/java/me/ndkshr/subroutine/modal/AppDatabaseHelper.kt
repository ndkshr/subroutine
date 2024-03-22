package me.ndkshr.subroutine.modal

import android.content.Context
import androidx.room.Room

object AppDatabaseHelper {

    var DATABASE: AppDatabase? = null
    fun init(ctx: Context) {
        if (DATABASE != null) return
        DATABASE = Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    const val DATABASE_NAME = "subroutine_db"
}