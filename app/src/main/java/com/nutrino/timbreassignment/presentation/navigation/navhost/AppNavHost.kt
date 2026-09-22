package com.nutrino.timbreassignment.presentation.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nutrino.timbreassignment.presentation.navigation.navroutes.ALLAUDIOSCREEN
import com.nutrino.timbreassignment.presentation.navigation.navroutes.HOMESCREEN
import com.nutrino.timbreassignment.presentation.screens.getallaudio.GetAllAudioScreen
import com.nutrino.timbreassignment.presentation.screens.homescreen.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HOMESCREEN,
        modifier = modifier
    ) {
        composable<HOMESCREEN> {
            HomeScreen(
                onTrimAudioClick = {
                    navController.navigate(ALLAUDIOSCREEN)
                }
            )
        }
        composable<ALLAUDIOSCREEN> {
            GetAllAudioScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
