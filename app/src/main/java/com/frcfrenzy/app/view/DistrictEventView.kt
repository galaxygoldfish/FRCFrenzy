package com.frcfrenzy.app.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.BackHand
import androidx.compose.material.icons.rounded.NavigateBefore
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.R
import com.frcfrenzy.app.components.EventListItem
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import com.frcfrenzy.app.viewmodel.DistrictViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun DistrictEventView(
    navController: NavController,
    districtCode: String,
    viewModel: DistrictViewModel = viewModel()
) {
    val pagerState = rememberPagerState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refreshDistrictEventList(districtCode) }
    )
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(true) {
        viewModel.refreshDistrictEventList(districtCode)
    }
    FRCFrenzyTheme(
        tonalElevatedStatus = true
    ) {
        Surface {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = String.format(
                                    stringResource(id = R.string.district_event_view_header_format),
                                    districtCode
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
                        repeat(6) { index ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
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
                    ) { pageNumber ->
                        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                            androidx.compose.animation.AnimatedVisibility(visible = !viewModel.isRefreshing) {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    item { Spacer(Modifier.height(10.dp)) }
                                    if (viewModel.districtEventList.indices.contains(pageNumber)) {
                                        items(viewModel.districtEventList[pageNumber]) { item ->
                                            EventListItem(
                                                eventName = item.name,
                                                location = "${item.city}, ${item.stateprov}, ${item.country}",
                                                startDate = item.dateStart,
                                                endDate = item.dateEnd,
                                                onClick = {}
                                            )
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