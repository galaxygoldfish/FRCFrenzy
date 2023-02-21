package com.frcfrenzy.app.model

import com.google.gson.annotations.SerializedName

data class MatchList(
    @SerializedName("Schedule")
    var schedule: List<MatchItem>
)

data class MatchItem(
    val description: String,
    val matchNumber: String,
    val startTime: String,
    val tournamentLevel: String,
    val teams: List<MatchParticipantItem>
)

data class MatchParticipantItem(
    val teamNumber: Int,
    val station: String,
    val surrogate: Boolean
)