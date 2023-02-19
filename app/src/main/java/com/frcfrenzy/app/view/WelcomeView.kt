package com.frcfrenzy.app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.Hardware
import androidx.compose.material.icons.rounded.MilitaryTech
import androidx.compose.material.icons.rounded.PrecisionManufacturing
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.R
import com.frcfrenzy.app.misc.NavDestination
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import com.tencent.mmkv.MMKV

@Composable
fun WelcomeView(navController: NavController) {
    FRCFrenzyTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2F)
                        .align(Alignment.TopCenter)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.SmartToy,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.33F)
                            .fillMaxHeight(),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Rounded.Hardware,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.5F)
                            .fillMaxHeight(),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Rounded.PrecisionManufacturing,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(1.0F)
                            .fillMaxHeight(),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(35.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        text = stringResource(id = R.string.welcome_slogan),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    FilledTonalButton(
                        onClick = {
                            MMKV.defaultMMKV().encode("ONBOARDING_COMPLETE", true)
                            navController.navigate(NavDestination.Home)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.welcome_move_on_button_text),
                            modifier = Modifier.padding(end = 10.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2F)
                        .align(Alignment.BottomCenter)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CalendarMonth,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.33F)
                            .fillMaxHeight(),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Rounded.MilitaryTech,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.5F)
                            .fillMaxHeight(),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Rounded.Celebration,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(1.0F)
                            .fillMaxHeight(),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }

}