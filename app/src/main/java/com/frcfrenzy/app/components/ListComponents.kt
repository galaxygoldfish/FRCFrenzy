package com.frcfrenzy.app.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.frcfrenzy.app.misc.parseAPIDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListItem(
    eventName: String,
    location: String,
    startDate: String,
    endDate: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        ),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.7F)) {
                Text(
                    text = eventName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "${parseAPIDateFormat(startDate)}-${parseAPIDateFormat(endDate)}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(0.7F)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamListItem(
    teamName: String,
    location: String,
    teamNumber: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        ),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .border(
                        border = BorderStroke(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ),
                        shape = MaterialTheme.shapes.small
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = teamNumber,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Text(
                    text = teamName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}