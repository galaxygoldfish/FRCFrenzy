package com.frcfrenzy.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.model.MatchItem
import com.frcfrenzy.app.model.TeamItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder.getNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {

    var currentEventItem by mutableStateOf<EventItem?>(null)
    var currentQualificationList  = mutableStateListOf<MatchItem>()
    var currentPlayoffList = mutableStateListOf<MatchItem>()
    var currentTeamList = mutableStateListOf<TeamItem>()
    var isRefreshing by mutableStateOf(false)

    var currentMatchViewingType by mutableStateOf(0)

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

    fun refreshMatchLists(eventCode: String) {
        isRefreshing = true
        CoroutineScope(Dispatchers.Default).launch {
            currentQualificationList.apply {
                clear()
                addAll(
                    networkService.getEventSchedule(
                        eventCode = eventCode,
                        tournamentLevel = "Qualification"
                    ).schedule
                )
            }
            currentPlayoffList.apply {
                clear()
                addAll(
                    networkService.getEventSchedule(
                        eventCode = eventCode,
                        tournamentLevel = "Playoff"
                    ).schedule
                )
            }
            isRefreshing = false
        }
    }

    fun refreshTeamList(eventCode: String) {
        isRefreshing = true
        currentTeamList.clear()
        CoroutineScope(Dispatchers.Default).launch {
            currentTeamList.addAll(
                networkService.getTeamList(eventCode = eventCode).teams
            )
            isRefreshing = false
        }
    }

}