package com.frcfrenzy.app.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.model.MatchItem
import com.frcfrenzy.app.model.RankingItem
import com.frcfrenzy.app.model.RankingList
import com.frcfrenzy.app.model.TeamItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder.getNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections.addAll

class EventViewModel : ViewModel() {

    var currentQualificationList  = mutableStateListOf<MatchItem>()
    var currentPlayoffList = mutableStateListOf<MatchItem>()
    var currentTeamList = mutableStateListOf<TeamItem>()
    var teamToNameMap = mutableMapOf<Int, String>()
    var currentRankingList = mutableStateListOf<Pair<String, RankingItem>>()

    var currentEventItem by mutableStateOf<EventItem?>(null)
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
        teamToNameMap.clear()
        CoroutineScope(Dispatchers.IO).launch {
            networkService.getTeamList(eventCode = eventCode).teams.apply {
                forEach { item ->
                    teamToNameMap[item.teamNumber] = item.nameShort
                }
                currentTeamList.addAll(this)
            }
            isRefreshing = false
        }
    }

    fun refreshRankings(eventCode: String) {
        isRefreshing = true
        currentRankingList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            networkService.getEventRankings(eventCode = eventCode).rankings.forEach { item ->
                teamToNameMap[item.teamNumber]?.let {
                    currentRankingList.add(Pair(it, item))
                }
            }
            isRefreshing = false
        }
    }

}