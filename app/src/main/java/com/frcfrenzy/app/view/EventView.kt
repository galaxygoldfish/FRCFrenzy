package com.frcfrenzy.app.view

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CorporateFare
import androidx.compose.material.icons.rounded.FactCheck
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.MilitaryTech
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.RssFeed
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.viewmodel.EventViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frcfrenzy.app.R
import com.frcfrenzy.app.components.AllianceCard
import com.frcfrenzy.app.components.AwardsCard
import com.frcfrenzy.app.components.CardWithIcon
import com.frcfrenzy.app.components.MatchCard
import com.frcfrenzy.app.components.TeamListItem
import com.frcfrenzy.app.misc.NavDestination
import com.frcfrenzy.app.misc.parseAPIDateFormat
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class
)
@Composable
fun EventView(
    navController: NavController,
    viewModel: EventViewModel = viewModel(),
    eventCode: String
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = {
            viewModel.apply {
                when (pagerState.currentPage) {
                    0 -> refreshEventOverview(eventCode)
                    1 -> refreshMatchLists(eventCode)
                    2 -> refreshTeamList(eventCode)
                    3 -> refreshRankings(eventCode)
                    4 -> refreshAllianceSelections(eventCode)
                    5 -> refreshAwardsList(eventCode)
                }
            }
        }
    )
    LaunchedEffect(true) {
        viewModel.apply {
            refreshEventOverview(eventCode)
            refreshMatchLists(eventCode)
            refreshTeamList(eventCode)
            refreshRankings(eventCode)
            refreshAllianceSelections(eventCode)
            refreshAwardsList(eventCode)
        }
    }
    FRCFrenzyTheme(tonalElevatedStatus = true) {
        Surface {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            AnimatedContent(targetState = viewModel.currentEventItem) {
                                Text(
                                    text = it?.name.toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                        )
                    )
                }
            ) { paddingValues ->
                Column(Modifier.padding(paddingValues)) {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        divider = {},
                        edgePadding = 0.dp
                    ) {
                        stringArrayResource(id = R.array.event_detail_tab_title).forEachIndexed { index, tabTitle ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                            ) {
                                Text(
                                    text = tabTitle,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(20.dp),
                                    color = if (pagerState.currentPage == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                        }
                    }
                    HorizontalPager(
                        pageCount = 6,
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pullRefresh(pullRefreshState)
                        ) {
                            AnimatedContent(targetState = page) {
                                when (it) {
                                    0 -> OverviewPage(viewModel)
                                    1 -> EventTeamPage(viewModel, navController)
                                    2 -> MatchPage(viewModel)
                                    3 -> RankingPage(viewModel)
                                    4 -> AlliancePage(viewModel)
                                    5 -> AwardsPage(viewModel)
                                }
                            }
                            PullRefreshIndicator(
                                refreshing = viewModel.isRefreshing,
                                state = pullRefreshState,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OverviewPage(viewModel: EventViewModel) {
    val context = LocalContext.current
    if (viewModel.currentEventItem != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = R.string.event_view_venue_section_title),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 15.dp, start = 19.dp)
            )
            CardWithIcon(
                icon = Icons.Rounded.CorporateFare,
                mainContent = {
                    Text(
                        text = viewModel.currentEventItem!!.venue,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                    )
                },
                modifier = Modifier.padding(top = 10.dp)
            )
            CardWithIcon(
                icon = Icons.Rounded.Map,
                mainContent = {
                    Text(
                        text = viewModel.currentEventItem!!.let {
                            "${it.address}\n${it.city}, ${it.stateprov}, ${it.country}"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                    )
                }
            )
            Text(
                text = stringResource(id = R.string.event_view_date_section_title),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 10.dp, start = 19.dp, bottom = 10.dp)
            )
            CardWithIcon(
                icon = Icons.Rounded.CalendarMonth,
                mainContent = {
                    Text(
                        text = viewModel.currentEventItem!!.let {
                            "${parseAPIDateFormat(it.dateStart)} - ${parseAPIDateFormat(it.dateEnd)}"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                    )
                }
            )
            CardWithIcon(
                icon = Icons.Rounded.Public,
                mainContent = {
                    Text(
                        text = viewModel.currentEventItem!!.timezone,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                    )
                }
            )
            Text(
                text = stringResource(id = R.string.event_view_web_content_section_title),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 10.dp, start = 19.dp, bottom = 10.dp)
            )
            if (viewModel.currentEventItem!!.website.isNotEmpty()) {
                CardWithIcon(
                    icon = Icons.Rounded.RssFeed,
                    mainContent = {
                        Text(
                            text = viewModel.currentEventItem!!.website,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp),
                            textDecoration = TextDecoration.Underline
                        )
                    },
                    onClick = {
                        context.startActivity(
                            Intent(ACTION_VIEW).apply {
                                data = Uri.parse(viewModel.currentEventItem!!.website)
                            }
                        )
                    }
                )
                // TODO add livestreams
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchPage(viewModel: EventViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(modifier = Modifier.padding(15.dp)) {
                FilterChip(
                    selected = viewModel.currentMatchViewingType == 0,
                    onClick = { viewModel.currentMatchViewingType = 0 },
                    label = { Text(text = stringResource(id = R.string.event_view_filter_qualification)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Timer,
                            contentDescription = null
                        )
                    }
                )
                FilterChip(
                    selected = viewModel.currentMatchViewingType == 1,
                    onClick = { viewModel.currentMatchViewingType = 1 },
                    label = { Text(text = stringResource(id = R.string.event_view_filter_playoff)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.MilitaryTech,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
        items(viewModel.currentQualificationList) { item ->
            AnimatedVisibility(visible = viewModel.currentMatchViewingType == 0) {
                MatchCard(matchItem = item)
            }
        }
        items(viewModel.currentPlayoffList) { item ->
            AnimatedVisibility(visible = viewModel.currentMatchViewingType == 1) {
                MatchCard(matchItem = item)
            }
        }
        item {
            AnimatedVisibility(
                visible = viewModel.let {
                    it.currentQualificationList.isEmpty() && it.currentMatchViewingType == 0 ||
                            it.currentPlayoffList.isEmpty() && it.currentMatchViewingType == 1
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PendingActions,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.event_view_schedule_empty_text),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventTeamPage(viewModel: EventViewModel, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { Spacer(Modifier.height(10.dp)) }
        items(viewModel.currentTeamList) { item ->
            TeamListItem(
                teamName = buildAnnotatedString { append(item.nameShort) },
                teamNumber = item.teamNumber.toString(),
                location = buildAnnotatedString {
                    append("${item.city}, ${item.stateProv}, ${item.country}")
                },
                onClick = {
                    navController.navigate("${NavDestination.TeamDetail}/${item.teamNumber}")
                }
            )
        }
    }
}

@Composable
fun RankingPage(viewModel: EventViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { Spacer(Modifier.height(10.dp)) }
        items(viewModel.currentRankingList) { item ->
            TeamListItem(
                teamName = buildAnnotatedString {
                    append(item.first)
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground.copy(0.6F))) {
                        append("  (${item.second.teamNumber})")
                    }
                },
                teamNumber = item.second.rank.toString(),
                location = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("RP: ")
                    }
                    append(item.second.sortOrder1.toString())
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("\t\tWLT: ")
                    }
                    append("${item.second.wins}-${item.second.losses}-${item.second.ties}")
                },
                onClick = {}
            )
        }
    }
}

@Composable
fun AlliancePage(viewModel: EventViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { Spacer(Modifier.height(10.dp)) }
        items(viewModel.currentAllianceList) { item ->
            AllianceCard(allianceItem = item)
        }
        if (viewModel.currentAwardsList.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FactCheck,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(40.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.event_view_alliance_selection_empty_text),
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun AwardsPage(viewModel: EventViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { Spacer(Modifier.height(10.dp)) }
        items(viewModel.currentAwardsList) { item ->
            AwardsCard(awardItem = item.second, teamName = item.first)
        }
        if (viewModel.currentAwardsList.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MilitaryTech,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(40.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.event_view_awards_empty_text),
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}