package me.ndkshr

import android.app.Application
import androidx.room.Room
import me.ndkshr.subroutine.modal.AppDatabase
import me.ndkshr.subroutine.modal.AppDatabaseHelper

class SubroutineApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabaseHelper.init(this)
    }
}
