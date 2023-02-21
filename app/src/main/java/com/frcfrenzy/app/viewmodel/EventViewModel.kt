package com.frcfrenzy.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder.getNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    var currentEventItem by mutableStateOf<EventItem?>(null)
    var isRefreshing by mutableStateOf(false)

    private val networkService = getNetworkService()

    fun refreshEventOverview(eventCode: String) {
        isRefreshing = true
        CoroutineScope(Dispatchers.Default).launch {
            currentEventItem = networkService.getEventList(
                excludeDistrictEvents = false,
                eventCode = eventCode
            ).events[0]
            isRefreshing = false
        }
    }

}