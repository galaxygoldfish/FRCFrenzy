package com.frcfrenzy.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.TeamItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder.getNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {

    var isRefreshing by mutableStateOf(false)

    var currentTeamItem by mutableStateOf<TeamItem?>(null)

    private val networkService = getNetworkService()

    fun refreshTeamList(teamNumber: Int) {
        isRefreshing = true
        CoroutineScope(Dispatchers.Default).launch {
            currentTeamItem = networkService.getTeamList(teamNumber = teamNumber).teams[0]
            isRefreshing = false
        }
    }

    fun refreshEventList(teamNumber: Int) {
        
    }

}