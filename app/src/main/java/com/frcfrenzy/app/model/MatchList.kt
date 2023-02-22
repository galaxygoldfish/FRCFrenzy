package com.frcfrenzy.app.model

import com.google.gson.annotations.SerializedName

data class MatchList(
    @SerializedName("Matches")
    var schedule: List<MatchItem>
)

data class MatchItem(
    val description: String,
    val matchNumber: String,
    val tournamentLevel: String,
    val scoreRedFinal: Int?,
    val scoreBlueFinal: Int?,
    val teams: List<MatchParticipantItem>
)

data class MatchParticipantItem(
    val teamNumber: Int,
    val station: String
)