package com.frcfrenzy.app.view.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.ShareLocation
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.viewmodel.HomeViewModel
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frcfrenzy.app.R
import com.frcfrenzy.app.view.home.DistrictPage
import com.frcfrenzy.app.view.home.HomePage
import com.frcfrenzy.app.view.home.OffseasonPage
import com.frcfrenzy.app.view.home.RegionalPage

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
                                        else -> stringResource(id = R.string.home_title_offseason_page)
                                    }
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
                        2 -> RegionalPage(navController = navController)
                        3 -> OffseasonPage(navController = navController)
                    }
                }
            }
        }
    }
}