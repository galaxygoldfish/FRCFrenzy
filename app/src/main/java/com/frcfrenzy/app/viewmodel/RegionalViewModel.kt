package com.frcfrenzy.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Year

class RegionalViewModel : ViewModel() {

    val regionalEventList = mutableListOf<SnapshotStateList<EventItem>>()
    var isRefreshing by mutableStateOf(false)

    private val networkService = NetworkServiceBuilder.getNetworkService()

    fun refreshRegionalEventList() {
        isRefreshing = true
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
            isRefreshing = false
        }
    }

}