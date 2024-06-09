package com.harsh_kumar.calllog.models

import android.content.Context

const val SHARED_PREFERENCES_FILE = "myFileForPhoneState"
const val PHONE_STATE = "phoneState"
fun editSharedPreferences(context: Context,value: String){
    val editor = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE).edit()
    editor.putString(PHONE_STATE,value)
    editor.apply()
}

fun readSharedPreferences(context: Context):String?{
    val reader = context.getSharedPreferences(SHARED_PREFERENCES_FILE,Context.MODE_PRIVATE)
    return reader.getString(PHONE_STATE,null)
}

enum class STATE {
    OFFHOOK,RINGING,IDLE,INCOMING
}