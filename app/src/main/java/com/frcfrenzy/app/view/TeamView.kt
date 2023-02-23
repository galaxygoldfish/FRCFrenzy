package com.frcfrenzy.app.view

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.PrecisionManufacturing
import androidx.compose.material.icons.rounded.RssFeed
import androidx.compose.material.icons.rounded.ShareLocation
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.frcfrenzy.app.viewmodel.TeamViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frcfrenzy.app.R
import com.frcfrenzy.app.components.CardWithIcon
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun TeamView(
    viewModel: TeamViewModel = viewModel(),
    navController: NavController,
    teamNumber: Int
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = { /*TODO*/ }
    )
    LaunchedEffect(true) {
        viewModel.apply {
            refreshTeamList(teamNumber)
        }
    }
    FRCFrenzyTheme(tonalElevatedStatus = true) {
        Surface {
            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        title = {
                            AnimatedVisibility(viewModel.currentTeamItem != null) {
                                Text(
                                    text = viewModel.currentTeamItem?.nameShort.toString(),
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
                        stringArrayResource(id = R.array.team_detail_tab_title).forEachIndexed { index, tabTitle ->
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
                        pageCount = 3,
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
                                    0 -> TeamOverviewPage(viewModel)
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
fun TeamOverviewPage(viewModel: TeamViewModel) {
    val context = LocalContext.current
    AnimatedVisibility(!viewModel.isRefreshing) {
        viewModel.currentTeamItem?.let {
            Column(modifier = Modifier.fillMaxSize()) {
                CardWithIcon(
                    icon = Icons.Rounded.Map,
                    mainContent = {
                        Text(
                            text = it.schoolName,
                            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp),
                        )
                    },
                    modifier = Modifier.padding(top = 10.dp)
                )
                CardWithIcon(
                    icon = Icons.Rounded.LocationOn,
                    mainContent = {
                        Text(
                            text = "${it.city}, ${it.stateProv}, ${it.country}",
                            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                        )
                    }
                )
                if (it.website.isNotBlank()) {
                    CardWithIcon(
                        icon = Icons.Rounded.RssFeed,
                        mainContent = {
                            Text(
                                text = it.website,
                                modifier = Modifier.padding(
                                    top = 15.dp,
                                    bottom = 15.dp,
                                    end = 15.dp
                                ),
                                textDecoration = TextDecoration.Underline
                            )
                        },
                        onClick = {
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(it.website)
                                }
                            )
                        }
                    )
                }
                CardWithIcon(
                    icon = Icons.Rounded.CalendarMonth,
                    mainContent = {
                        Text(
                            text = String.format(
                                stringResource(id = R.string.team_view_rookie_year_prefix),
                                it.rookieYear
                            ),
                            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                        )
                    }
                )
                if (it.robotName.isNotBlank()) {
                    CardWithIcon(
                        icon = Icons.Rounded.PrecisionManufacturing,
                        mainContent = {
                            Text(
                                text = it.robotName,
                                modifier = Modifier.padding(
                                    top = 15.dp,
                                    bottom = 15.dp,
                                    end = 15.dp
                                )
                            )
                        }
                    )
                }
                CardWithIcon(
                    icon = Icons.Rounded.ShareLocation,
                    mainContent = {
                        Text(
                            text = if (!it.districtCode.isNullOrBlank()) {
                                String.format(
                                    stringResource(id = R.string.team_view_district_prefix),
                                    it.districtCode
                                )
                            } else {
                                stringResource(id = R.string.team_view_no_district)
                            },
                            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TeamEventPage(viewModel: TeamViewModel) {

}

@Composable
fun TeamAwardPage(viewModel: TeamViewModel) {

}