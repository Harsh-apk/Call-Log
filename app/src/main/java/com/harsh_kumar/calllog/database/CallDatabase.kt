package com.harsh_kumar.calllog.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harsh_kumar.calllog.models.Call


@Database(entities = [Call::class], version = 1)
abstract class CallDatabase :RoomDatabase() {
    companion object{
        const val name = "Call_DB"
    }

    abstract fun getCallDao():CallDAO
}