package com.frcfrenzy.app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionalViewModel : ViewModel() {

    val regionalEventList = mutableListOf<SnapshotStateList<EventItem>>()

    private val networkService = NetworkServiceBuilder.getNetworkService()

    fun refreshRegionalEventList() {
        regionalEventList.clear()
        CoroutineScope(Dispatchers.Default).launch {
            repeat(6) { index ->
                mutableStateListOf<EventItem>().let { stateList ->
                    stateList.addAll(
                        networkService.getEventList(
                            excludeDistrictEvents = true,
                            tournamentType = "Regional",
                            weekNumber = index + 1
                        ).events
                    )
                    regionalEventList.add(stateList)
                }
            }
        }
    }

}