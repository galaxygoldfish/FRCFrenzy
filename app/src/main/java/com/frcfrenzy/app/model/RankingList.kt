package com.frcfrenzy.app.model

import com.google.gson.annotations.SerializedName

data class RankingList(
    @SerializedName("Rankings")
    val rankings: List<RankingItem>
)

data class RankingItem(
    val rank: Int,
    val teamNumber: Int,
    val sortOrder1: Double,
    val wins: Int,
    val losses: Int,
    val ties: Int,
    val matchesPlayed: Int,
    val qualAverage: Double
)