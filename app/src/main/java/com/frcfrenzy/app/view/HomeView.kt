package com.frcfrenzy.app.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CalendarViewMonth
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShareLocation
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.viewmodel.HomeViewModel
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frcfrenzy.app.R
import com.tencent.mmkv.MMKV
import java.time.Year

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    FRCFrenzyTheme(
        tonalElevatedStatus = true,
        tonalElevatedNav = true
    ) {
        Surface {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            AnimatedContent(
                                targetState = viewModel.currentNavigationTab
                            ) { state ->
                                Text(
                                    text = when (state) {
                                        0 -> stringResource(id = R.string.home_title_home_page)
                                        1 -> stringResource(id = R.string.home_title_districts_page)
                                        2 -> stringResource(id = R.string.home_title_regionals_page)
                                        3 -> stringResource(id = R.string.home_title_offseason_page)
                                        else -> stringResource(id = R.string.home_title_teams_page)
                                    }
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
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = viewModel.currentNavigationTab == 0,
                            onClick = { viewModel.currentNavigationTab = 0 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.Home,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = stringResource(id = R.string.home_title_home_page))
                            }
                        )
                        NavigationBarItem(
                            selected = viewModel.currentNavigationTab == 1,
                            onClick = { viewModel.currentNavigationTab = 1 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.ShareLocation,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = stringResource(id = R.string.home_title_districts_page))
                            }
                        )
                        NavigationBarItem(
                            selected = viewModel.currentNavigationTab == 2,
                            onClick = { viewModel.currentNavigationTab = 2 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.Public,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = stringResource(id = R.string.home_title_regionals_page))
                            }
                        )
                        NavigationBarItem(
                            selected = viewModel.currentNavigationTab == 3,
                            onClick = { viewModel.currentNavigationTab = 3 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.Upcoming,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = stringResource(id = R.string.home_title_offseason_page))
                            }
                        )
                        NavigationBarItem(
                            selected = viewModel.currentNavigationTab == 4,
                            onClick = { viewModel.currentNavigationTab = 4 },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.Diversity3,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = stringResource(id = R.string.home_title_teams_page))
                            }
                        )
                    }
                }
            ) { paddingValues ->
                AnimatedContent(
                    targetState = viewModel.currentNavigationTab,
                    modifier = Modifier.padding(paddingValues)
                ) { state ->
                    when (state) {
                        0 -> HomePage()
                        1 -> DistrictPage(navController = navController)
                        2 -> RegionalPage()
                        3 -> OffseasonPage(navController = navController)
                        4 -> TeamPage()
                    }
                }
            }
        }
    }
}