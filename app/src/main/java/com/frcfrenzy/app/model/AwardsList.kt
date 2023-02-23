package com.frcfrenzy.app.model

import com.google.gson.annotations.SerializedName

data class AwardsList(
    @SerializedName("Awards")
    val awards: List<AwardItem>
)

data class AwardItem(
    val name: String,
    val teamNumber: Int,
    val series: Int
)