package com.frcfrenzy.app.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NavigateNext
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.frcfrenzy.app.viewmodel.DistrictViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frcfrenzy.app.misc.NavDestination

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun DistrictPage(
    viewModel: DistrictViewModel = viewModel(),
    navController: NavController
) {
    val swipeRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refreshDistrictList() }
    )
    LaunchedEffect(true) {
        viewModel.refreshDistrictList()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(swipeRefreshState)
    ) {
        AnimatedVisibility(visible = viewModel.districtList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                item { Spacer(Modifier.height(10.dp)) }
                items(viewModel.districtList) { item ->
                    val districtName = item.name
                        .removePrefix("FIRST")
                        .removePrefix("In ")
                        .removeSuffix("Robotics")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("${NavDestination.DistrictOverview}/${item.code}/$districtName")
                            }
                            .padding(horizontal = 15.dp, vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
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
                                    text = item.code,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Text(
                                text = districtName,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(start = 15.dp)
                            )
                        }
                        Icon(
                            imageVector = Icons.Rounded.NavigateNext,
                            contentDescription = null
                        )
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = viewModel.isRefreshing,
            state = swipeRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}