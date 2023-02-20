package com.frcfrenzy.app.model

data class TeamList(
    val teams: List<TeamItem>,
    val teamCountTotal: Int,
    val teamCountPage: Int,
    val pageCurrent: Int,
    val pageTotal: Int
)

data class TeamItem(
    val schoolName: String,
    val website: String,
    val homeCMP: String,
    val teamNumber: Int,
    val nameFull: String,
    val nameShort: String,
    val city: String,
    val stateProv: String,
    val country: String,
    val rookieYear: Int,
    val robotName: String,
    val districtCode: String
)
