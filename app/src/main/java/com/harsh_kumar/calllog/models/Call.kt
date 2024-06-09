package com.harsh_kumar.calllog.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity
data class Call(var number:String,var type:String){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
    lateinit var time :String
    fun setCurrentTime(){
        time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString()
    }

}


enum class CALL_TYPES{
   INCOMING,OUTGOING
}
