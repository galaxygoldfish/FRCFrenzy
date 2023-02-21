package com.frcfrenzy.app.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.R
import com.frcfrenzy.app.components.EventListItem
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import com.frcfrenzy.app.viewmodel.DistrictViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frcfrenzy.app.components.TeamListItem
import com.frcfrenzy.app.misc.NavDestination
import com.tencent.mmkv.MMKV
import java.time.Year

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class, ExperimentalAnimationApi::class
)
@Composable
fun DistrictOverview(
    navController: NavController,
    districtCode: String,
    districtName: String,
    viewModel: DistrictViewModel = viewModel()
) {
    val pagerState = rememberPagerState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = { 
            when (pagerState.currentPage) {
                0 -> viewModel.refreshDistrictTeamList(districtCode)
                else -> viewModel.refreshDistrictEventList(districtCode)
            }
        }
    )
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(true) {
        viewModel.apply {
            refreshDistrictTeamList(districtCode)
            refreshDistrictEventList(districtCode)
        }
    }
    FRCFrenzyTheme(
        tonalElevatedStatus = true
    ) {
        Surface {
            Scaffold(
                topBar = {
                    MediumTopAppBar(
                        title = {
                            Text(
                                text = String.format(
                                    stringResource(id = R.string.district_event_view_header_format),
                                    districtName
                                ),
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        actions = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.DateRange,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp).padding(vertical = 5.dp, horizontal = 5.dp)
                                )
                                Text(
                                    text = MMKV.defaultMMKV().decodeString("CURRENT_YEAR", Year.now().value.toString())!!,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                        )
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        divider = {},
                        edgePadding = 0.dp
                    ) {
                        Tab(
                            selected = pagerState.currentPage == 0,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.district_event_view_team_header),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(20.dp),
                                color = if (pagerState.currentPage == 0) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                        repeat(6) { index ->
                            Tab(
                                selected = pagerState.currentPage == index + 1,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index + 1)
                                    }
                                }
                            ) {
                                Text(
                                    text = String.format(
                                        stringResource(id = R.string.page_regional_tab_week_format),
                                        index + 1
                                    ),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(20.dp),
                                    color = if (pagerState.currentPage == index + 1) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                        }
                    }
                    HorizontalPager(
                        pageCount = 7,
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { pageNumber ->
                        AnimatedContent(targetState = pageNumber) { state ->
                            Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                                if (state == 0) {
                                    androidx.compose.animation.AnimatedVisibility(visible = !viewModel.isRefreshing) {
                                        LazyColumn {
                                            item { Spacer(Modifier.height(10.dp)) }
                                            items(viewModel.districtTeamList) { item ->
                                                TeamListItem(
                                                    teamName = item.nameShort,
                                                    teamNumber = item.teamNumber.toString(),
                                                    location = "${item.city}, ${item.stateProv}, ${item.country}",
                                                    onClick = {}
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    androidx.compose.animation.AnimatedVisibility(visible = !viewModel.isRefreshing) {
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            item { Spacer(Modifier.height(10.dp)) }
                                            if (viewModel.districtEventList.indices.contains(
                                                    pageNumber - 1
                                                )
                                            ) {
                                                items(viewModel.districtEventList[pageNumber - 1]) { item ->
                                                    EventListItem(
                                                        eventName = item.name,
                                                        location = "${item.city}, ${item.stateprov}, ${item.country}",
                                                        startDate = item.dateStart,
                                                        endDate = item.dateEnd,
                                                        onClick = {
                                                            navController.navigate("${NavDestination.EventView}/${item.code}")
                                                        }
                                                    )
                                                }
                                            }
                                        }
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
}