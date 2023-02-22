package com.frcfrenzy.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.networking.NetworkServiceBuilder.getNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var currentNavigationTab by mutableStateOf(0)

}