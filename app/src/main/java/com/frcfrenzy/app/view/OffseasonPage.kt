package com.frcfrenzy.app.view

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.viewmodel.OffseasonViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frcfrenzy.app.R
import com.frcfrenzy.app.components.EventListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun OffseasonPage(
    navController: NavController,
    viewModel: OffseasonViewModel = viewModel()
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refreshOffseasonList(navController.context) }
    )
    LaunchedEffect(true) {
        viewModel.refreshOffseasonList(navController.context)
    }
    Surface {
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            Column {
                AnimatedVisibility(visible = viewModel.offseasonList.isNotEmpty()) {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                        edgePadding = 0.dp,
                        divider = {},
                    ) {
                        repeat(viewModel.offseasonList.size) { index ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                            ) {
                                Text(
                                    text = viewModel.offseasonList[index].first,
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
                }
                HorizontalPager(
                    pageCount = viewModel.offseasonList.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { pageNumber ->
                    AnimatedVisibility(visible = !viewModel.isRefreshing && viewModel.offseasonList.indices.contains(pageNumber)) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item { Spacer(Modifier.height(10.dp)) }
                            items(viewModel.offseasonList[pageNumber].second) { item ->
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
            }
            PullRefreshIndicator(
                refreshing = viewModel.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}