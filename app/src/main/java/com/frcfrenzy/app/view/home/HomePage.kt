package com.frcfrenzy.app.view.home

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.frcfrenzy.app.R

@Composable
fun HomePage() {
    Surface {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_first_energize_logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(20.dp)
            )
            LinkCard(
                icon = painterResource(id = R.drawable.first_icon_logo),
                text = stringResource(id = R.string.page_home_first_inspires_card_label),
                link = "https://firstinspires.org"
            )
            LinkCard(
                iconVector = Icons.Rounded.Bolt,
                text = stringResource(id = R.string.page_home_first_season_info_card_label),
                link = "https://info.firstinspires.org/first-energize-season"
            )
            LinkCard(
                iconVector = Icons.Rounded.SmartToy,
                text = stringResource(id = R.string.page_home_first_events_card_label),
                link = "https://frc-events.firstinspires.org"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkCard(
    icon: Painter? = null,
    iconVector: ImageVector? = null,
    text: String,
    link: String
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        ),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            context.startActivity(
                Intent(ACTION_VIEW).apply {
                    data = Uri.parse(link)
                }
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            iconVector?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
    }
}