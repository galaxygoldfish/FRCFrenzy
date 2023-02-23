package com.frcfrenzy.app.model

import com.google.gson.annotations.SerializedName

data class AllianceList(
    @SerializedName("Alliances")
    val alliances: List<AllianceItem>
)

data class AllianceItem(
    val name: String,
    val number: Int,
    val captain: Int,
    val round1: Int,
    val round2: Int
)