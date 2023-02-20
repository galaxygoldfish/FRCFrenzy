package com.frcfrenzy.app.misc

fun parseAPIDateFormat(input: String) : String {
    val dateSection = input.split("T")[0]
    val separatedDate = dateSection.split("-")
    return "${separatedDate[1].removePrefix("0")}/${separatedDate[2].removePrefix("0")}"
}