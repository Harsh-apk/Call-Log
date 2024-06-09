package com.harsh_kumar.calllog.views

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harsh_kumar.calllog.viewModels.MainViewmodel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.harsh_kumar.calllog.MainApplication
import com.harsh_kumar.calllog.models.CALL_TYPES
import com.harsh_kumar.calllog.models.Call


@Composable
fun MainScreen(){
    val viewmodel:MainViewmodel = viewModel()
    val allCalls by viewmodel.callList.observeAsState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.LightGray), contentAlignment = Alignment.Center){

        if(allCalls!=null && allCalls!!.isEmpty()){
            Text(text = "Nothing to show")
        }else{
        allCalls?.let {
            LazyColumn(content = {
                itemsIndexed(it){index:Int,item:Call->
                    CallItem(item, onDelete = {
                        viewmodel.deleteCall(item)
                    })

                }
            })
        }?:Text(text = "Loading ...")}
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CallItem(call: Call,onDelete:()->Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(color = Color.White)
        .padding(10.dp)
        .combinedClickable(
            onClick = { println("click\n\n\nclick") },
            onLongClick = { onDelete() }
        ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(text = call.type.toLowerCase(Locale.current),modifier = Modifier.padding(5.dp))
        Text(text = call.number,modifier = Modifier.padding(5.dp))

        Text(text=call.time, modifier = Modifier.padding(5.dp))

    }
}