package com.frcfrenzy.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.DistrictItem
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder.getNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DistrictViewModel : ViewModel() {

    var isRefreshing by mutableStateOf(false)
    val districtList = mutableStateListOf<DistrictItem>()
    val districtEventList = mutableListOf<SnapshotStateList<EventItem>>()

    private val networkService = getNetworkService()

    fun refreshDistrictList() {
        isRefreshing = true
        districtList.clear()
        CoroutineScope(Dispatchers.Default).launch {
            districtList.addAll(networkService.getDistrictList().districts)
            isRefreshing = false
        }
    }

    fun refreshDistrictEventList(district: String) {
        isRefreshing = true
        districtEventList.clear()
        CoroutineScope(Dispatchers.Default).launch {
            repeat(6) { index ->
                mutableStateListOf<EventItem>().let {
                    it.addAll(
                        networkService.getEventList(
                            excludeDistrictEvents = false,
                            districtCode = district,
                            weekNumber = index + 1
                        ).events
                    )
                    districtEventList.add(it)
                }
            }
            isRefreshing = false
        }
    }

}