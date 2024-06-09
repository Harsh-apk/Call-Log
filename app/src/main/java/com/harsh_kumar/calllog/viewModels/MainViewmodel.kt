package com.harsh_kumar.calllog.viewModels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harsh_kumar.calllog.MainApplication
import com.harsh_kumar.calllog.models.Call
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewmodel() :ViewModel() {
    val dao = MainApplication.callDatabase.getCallDao()
    var callList : LiveData<List<Call>> = dao.getAllCalls()

    fun deleteCall(call: Call){
        Toast.makeText(MainApplication.appContext,"Deleting ${call.number}",Toast.LENGTH_SHORT).show()
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteCall(call)
        }
    }

}