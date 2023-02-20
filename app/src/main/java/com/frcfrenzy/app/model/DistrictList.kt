package com.frcfrenzy.app.model

data class DistrictList(
    val districts: List<DistrictItem>,
    val districtCount: Int
)

data class DistrictItem(
    val name: String,
    val code: String
)