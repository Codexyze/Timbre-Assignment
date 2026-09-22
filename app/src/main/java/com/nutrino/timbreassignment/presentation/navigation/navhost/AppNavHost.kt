package com.nutrino.timbreassignment.presentation.navigation.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nutrino.timbreassignment.presentation.navigation.navroutes.ALLAUDIOSCREEN
import com.nutrino.timbreassignment.presentation.navigation.navroutes.ALLVIDEOSCREEN
import com.nutrino.timbreassignment.presentation.navigation.navroutes.AUDIOTRIMMERSCREEN
import com.nutrino.timbreassignment.presentation.navigation.navroutes.HOMESCREEN
import com.nutrino.timbreassignment.presentation.navigation.navroutes.VIDEOTRIMMERSCREEN
import com.nutrino.timbreassignment.presentation.screens.audiotrimmer.AudioTrimmerScreen
import com.nutrino.timbreassignment.presentation.screens.getallaudio.GetAllAudioScreen
import com.nutrino.timbreassignment.presentation.screens.getallvideo.GetAllVideoScreen
import com.nutrino.timbreassignment.presentation.screens.homescreen.HomeScreen
import com.nutrino.timbreassignment.presentation.screens.videotrimmer.VideoTrimmerScreen

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
                },
                onTrimVideoClick = {
                    navController.navigate(ALLVIDEOSCREEN)
                }
            )
        }
        composable<ALLAUDIOSCREEN> {
            GetAllAudioScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSongClick = { song ->
                    val durationMs = song.duration?.toLongOrNull() ?: 0L
                    navController.navigate(
                        AUDIOTRIMMERSCREEN(
                            songPath = song.path,
                            songTitle = song.title ?: "Audio",
                            songDurationMs = durationMs
                        )
                    )
                }
            )
        }
        composable<AUDIOTRIMMERSCREEN> { backStackEntry ->
            val route = backStackEntry.toRoute<AUDIOTRIMMERSCREEN>()
            AudioTrimmerScreen(
                songPath = route.songPath,
                songTitle = route.songTitle,
                songDurationMs = route.songDurationMs,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable<ALLVIDEOSCREEN> {
            GetAllVideoScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onVideoClick = { video ->
                    val durationMs = video.duration.toLongOrNull() ?: 0L
                    navController.navigate(
                        VIDEOTRIMMERSCREEN(
                            videoPath = video.path,
                            videoTitle = video.title.ifBlank { video.fileName },
                            videoDurationMs = durationMs
                        )
                    )
                }
            )
        }
        composable<VIDEOTRIMMERSCREEN> { backStackEntry ->
            val route = backStackEntry.toRoute<VIDEOTRIMMERSCREEN>()
            VideoTrimmerScreen(
                videoPath = route.videoPath,
                videoTitle = route.videoTitle,
                videoDurationMs = route.videoDurationMs,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
