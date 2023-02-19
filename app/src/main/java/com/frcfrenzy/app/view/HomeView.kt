package com.frcfrenzy.app.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShareLocation
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frcfrenzy.app.viewmodel.HomeViewModel
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.frcfrenzy.app.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    FRCFrenzyTheme(tonalElevatedSystemBars = true) {
        Surface {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            AnimatedContent(
                                targetState = viewModel.currentTab
                            ) { state ->
                                Text(
                                    text = when (state) {
                                        0 -> stringResource(id = R.string.home_title_home_page)
                                        1 -> stringResource(id = R.string.home_title_districts_page)
                                        2 -> stringResource(id = R.string.home_title_regionals_page)
                                        else -> stringResource(id = R.string.home_title_teams_page)
                                    }
                                )
                            }
                        },
                        actions = {
                          IconButton(onClick = { /*TODO*/ }) {
                              Icon(
                                  imageVector = Icons.Rounded.Settings,
                                  contentDescription = null
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
                            selected = viewModel.currentTab == 0,
                            onClick = { viewModel.currentTab = 0 },
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
                            selected = viewModel.currentTab == 1,
                            onClick = { viewModel.currentTab = 1 },
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
                            selected = viewModel.currentTab == 2,
                            onClick = { viewModel.currentTab = 2 },
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
                            selected = viewModel.currentTab == 3,
                            onClick = { viewModel.currentTab = 3 },
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
                    targetState = viewModel.currentTab,
                    modifier = Modifier.padding(paddingValues)
                ) { state ->
                    when (state) {
                        0 -> HomePage()
                        1 -> DistrictPage()
                        2 -> RegionalPage()
                        3 -> TeamPage()
                    }
                }
            }
        }
    }
}