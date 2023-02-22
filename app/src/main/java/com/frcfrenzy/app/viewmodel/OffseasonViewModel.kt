package com.frcfrenzy.app.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder.getNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.frcfrenzy.app.R
class OffseasonViewModel : ViewModel() {

    val offseasonList = mutableStateListOf<Pair<String, SnapshotStateList<EventItem>>>()
    var isRefreshing by mutableStateOf(false)

    private val networkService = getNetworkService()

    fun refreshOffseasonList(context: Context) {
        isRefreshing = true
        offseasonList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val remoteOffseasonEvents = networkService.getEventList(
                excludeDistrictEvents = false,
                tournamentType = "OffseasonWithAzureSync"
            ).events
            val monthListByName = context.resources.getStringArray(
                R.array.month_list_offseason_event
            )
            monthListByName.forEachIndexed { index, month ->
                val currentEventListForMonth = mutableStateListOf<EventItem>()
                remoteOffseasonEvents.forEach { event ->
                    if (event.dateStart.split("-")[1]
                        .removePrefix("0").toInt() == (index + 1)) {
                        currentEventListForMonth.add(event)
                    }
                }
                if (currentEventListForMonth.isNotEmpty()) {
                    offseasonList.add(Pair(month, currentEventListForMonth))
                }
            }
            isRefreshing = false
        }
    }

}