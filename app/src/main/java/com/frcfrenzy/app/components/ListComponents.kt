package com.frcfrenzy.app.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.frcfrenzy.app.misc.parseAPIDateFormat
import com.frcfrenzy.app.model.AllianceItem
import com.frcfrenzy.app.model.AwardItem
import com.frcfrenzy.app.model.MatchItem
import com.frcfrenzy.app.theme.getAllianceColorBLUE
import com.frcfrenzy.app.theme.getAllianceColorRED

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
            Column(modifier = Modifier.fillMaxWidth(0.65F)) {
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
    teamName: AnnotatedString,
    location: AnnotatedString,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithIcon(
    icon: ImageVector,
    mainContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
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
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(20.dp)
            )
            mainContent.invoke()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchCard(
    matchItem: MatchItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val redWon = (matchItem.scoreRedFinal ?: 0) > (matchItem.scoreBlueFinal ?: 0)
    val blueWon = (matchItem.scoreBlueFinal ?: 0) > (matchItem.scoreRedFinal ?: 0)
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
        Text(
            text = matchItem.description,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(15.dp)
        )
        Row {
            Column {
                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.titleMedium) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth(0.75F)
                                .padding(start = 15.dp, bottom = 15.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    MaterialTheme
                                        .getAllianceColorRED()
                                        .copy(0.4F)
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 3.dp,
                                        color = MaterialTheme.getAllianceColorRED()
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Text(
                                text = matchItem.teams[0].teamNumber.toString(),
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    top = 15.dp,
                                    bottom = 15.dp
                                )
                            )
                            Text(
                                text = matchItem.teams[1].teamNumber.toString(),
                                modifier = Modifier.padding(start = 15.dp)
                            )
                            Text(
                                text = matchItem.teams[2].teamNumber.toString(),
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(bottom = 15.dp, end = 15.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    if (redWon) {
                                        MaterialTheme
                                            .getAllianceColorRED()
                                            .copy(0.4F)
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 3.dp,
                                        color = if (redWon) {
                                            MaterialTheme.getAllianceColorRED()
                                        } else {
                                            Color.Transparent
                                        }
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Text(
                                text = matchItem.scoreRedFinal.toString(),
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth(0.75F)
                                .padding(bottom = 15.dp, start = 15.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    MaterialTheme
                                        .getAllianceColorBLUE()
                                        .copy(0.4F)
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 3.dp,
                                        color = MaterialTheme.getAllianceColorBLUE()
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Text(
                                text = matchItem.teams[3].teamNumber.toString(),
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                    top = 15.dp,
                                    bottom = 15.dp
                                )
                            )
                            Text(
                                text = matchItem.teams[4].teamNumber.toString(),
                                modifier = Modifier.padding(start = 15.dp)
                            )
                            Text(
                                text = matchItem.teams[5].teamNumber.toString(),
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(bottom = 15.dp, end = 15.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    if (blueWon) {
                                        MaterialTheme
                                            .getAllianceColorBLUE()
                                            .copy(0.3F)
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .border(
                                    border = BorderStroke(
                                        width = 3.dp,
                                        color = if (blueWon) {
                                            MaterialTheme.getAllianceColorBLUE()
                                        } else {
                                            Color.Transparent
                                        }
                                    ),
                                    shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Text(
                                text = matchItem.scoreBlueFinal.toString(),
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllianceCard(
    allianceItem: AllianceItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
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
        Text(
            text = allianceItem.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(15.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, bottom = 15.dp, end = 15.dp)
        ) {
            listOf(allianceItem.captain, allianceItem.round1, allianceItem.round2).forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(
                            when (index) {
                                0 -> 0.3F
                                1 -> 0.4F
                                else -> 0.8F
                            }
                        )
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.secondary.copy(0.2F))
                        .border(
                            border = BorderStroke(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            shape = MaterialTheme.shapes.medium
                        ),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.toString(),
                        modifier = Modifier.padding(vertical = 15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AwardsCard(
    awardItem: AwardItem,
    teamName: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = awardItem.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(15.dp)
        )
        Text(
            text = buildAnnotatedString {
                append(teamName)
                withStyle(
                    SpanStyle(color = MaterialTheme.colorScheme.onBackground.copy(0.7F))
                ) {
                    append("\t\t${awardItem.teamNumber}")
                }
            },
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
        )
    }
}