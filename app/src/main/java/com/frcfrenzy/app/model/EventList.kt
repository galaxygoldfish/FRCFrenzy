package com.frcfrenzy.app.model

import com.google.gson.annotations.SerializedName

data class EventList(
    @SerializedName("Events")
    val events: List<EventItem>,
    val eventCount: Int
)

data class EventItem(
    val address: String,
    val website: String,
    val timezone: String,
    val code: String,
    val name: String,
    val type: String,
    val districtCode: String?,
    val venue: String,
    val city: String,
    val stateprov: String,
    val country: String,
    val dateStart: String,
    val dateEnd: String
)
