package com.frcfrenzy.app.view

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CorporateFare
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.RssFeed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.viewmodel.EventViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frcfrenzy.app.R
import com.frcfrenzy.app.components.CardWithIcon
import com.frcfrenzy.app.misc.parseAPIDateFormat
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun EventView(
    navController: NavController,
    viewModel: EventViewModel = viewModel(),
    eventCode: String
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    LaunchedEffect(true) {
        viewModel.refreshEventOverview(eventCode)
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
                        pageCount = 5
                    ) {
                        OverviewPage(viewModel)
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