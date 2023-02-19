package com.frcfrenzy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.frcfrenzy.app.misc.NavDestination
import com.frcfrenzy.app.theme.FRCFrenzyTheme
import com.frcfrenzy.app.view.WelcomeView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberAnimatedNavController()
            FRCFrenzyTheme {
                NavigationHost()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun NavigationHost() {
        AnimatedNavHost(
            navController = navController,
            startDestination = NavDestination.WelcomeScreen,
            modifier = Modifier.fillMaxSize(),
            builder = {
                composable(NavDestination.WelcomeScreen) {
                    WelcomeView(navController)
                }
            }
        )
    }

}