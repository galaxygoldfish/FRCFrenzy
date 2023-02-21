package com.frcfrenzy.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AllianceColorREDLight = Color(0xFFEF9A9A)
val AllianceColorREDDark = Color(0xFFEF5350)
val AllianceColorBLUELight = Color(0xFF90CAF9)
val AllianceColorBLUEDark = Color(0xFF42A5F5)

@Composable
fun MaterialTheme.getAllianceColorRED() : Color = if (isSystemInDarkTheme()) {
    AllianceColorREDDark
} else {
    AllianceColorREDLight
}


@Composable
fun MaterialTheme.getAllianceColorBLUE() : Color = if (isSystemInDarkTheme()) {
    AllianceColorBLUEDark
} else {
    AllianceColorBLUELight
}