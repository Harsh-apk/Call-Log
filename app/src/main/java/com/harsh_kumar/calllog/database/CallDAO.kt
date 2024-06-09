package com.harsh_kumar.calllog.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.harsh_kumar.calllog.models.Call

@Dao
interface CallDAO {
    @Query("SELECT * FROM CALL")
    fun getAllCalls():LiveData<List<Call>>

    @Insert
    fun addCall(call: Call)

    @Delete
    fun deleteCall(call: Call)

}