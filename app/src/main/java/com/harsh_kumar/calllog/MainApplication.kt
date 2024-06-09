package com.harsh_kumar.calllog

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.harsh_kumar.calllog.database.CallDatabase

class MainApplication:Application() {

    companion object {
        lateinit var callDatabase: CallDatabase
        lateinit var appContext : Context
    }
    override fun onCreate() {
        super.onCreate()
        callDatabase = Room.databaseBuilder(applicationContext,CallDatabase::class.java,CallDatabase.name).build()
        appContext = applicationContext
    }
}