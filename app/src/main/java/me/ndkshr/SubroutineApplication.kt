package me.ndkshr

import android.app.Application
import me.ndkshr.subroutine.modal.local.AppDatabaseHelper

class SubroutineApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabaseHelper.init(this)
    }
}
