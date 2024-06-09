package com.harsh_kumar.calllog.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast
import com.harsh_kumar.calllog.MainApplication
import com.harsh_kumar.calllog.models.CALL_TYPES
import com.harsh_kumar.calllog.models.Call
import com.harsh_kumar.calllog.models.SHARED_PREFERENCES_FILE
import com.harsh_kumar.calllog.models.STATE
import com.harsh_kumar.calllog.models.editSharedPreferences
import com.harsh_kumar.calllog.models.readSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.contracts.contract

class CallReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.intent.action.PHONE_STATE"){
            //println(intent.getStringExtra(TelephonyManager.EXTRA_STATE))
            //println("shared prefernces: ${readSharedPreferences(context!!)}")
            var state:String? = null
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
                if(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!=null && context!=null){
                    state = STATE.RINGING.toString()
                    editSharedPreferences(context,state)

                }
            }else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                if(context!=null){
                    state = readSharedPreferences(context)
                    if(state!=null && state.equals(STATE.RINGING.toString())){
                        //incoming call uthaya hai, kuch mat karo
                    }else if((state == null || state.equals(STATE.IDLE.toString()) ) && intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!=null ){
                        state = STATE.OFFHOOK.toString()
                        editSharedPreferences(context,state)
                    }
                }
            }else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
                if(context!=null && intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!=null ){
                    state = readSharedPreferences(context)
                    println("from idle $state")
                    if(state!=null && state.equals(STATE.OFFHOOK.toString())){
                        writeToDatabase(intent,context,CALL_TYPES.OUTGOING)
                    }else if(state!=null && state.equals(STATE.RINGING.toString())){
                        writeToDatabase(intent,context,CALL_TYPES.INCOMING)
                    }
                    state = STATE.IDLE.toString()
                    editSharedPreferences(context,state)
                }
            }
        }
    }
}

fun writeToDatabase(intent:Intent,context: Context, callType: CALL_TYPES){
    val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
    if(number!=null){
        Toast.makeText(context, "$callType $number", Toast.LENGTH_SHORT).show()
        println("$callType $number")
        //save to room
        val call = Call(number,callType.toString() )
        call.setCurrentTime()
        GlobalScope.launch(Dispatchers.IO) {
            MainApplication.callDatabase.getCallDao().addCall(call)
        }

    }
}



//if (intent?.action == "android.intent.action.PHONE_STATE") {
//            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
//                if (number != null) {
//                    Toast.makeText(context, "Incoming call $number", Toast.LENGTH_SHORT).show()
//                    println(number)
//                    //save to room
//                    val call = Call(number, CALL_TYPES.INCOMING.toString())
//                    call.setCurrentTime()
//                    GlobalScope.launch(Dispatchers.IO) {
//                        MainApplication.callDatabase.getCallDao().addCall(call)
//                    }
//                }
//            }else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
//                val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
//                println("number from offHook :$number")
//            }
//
//            }