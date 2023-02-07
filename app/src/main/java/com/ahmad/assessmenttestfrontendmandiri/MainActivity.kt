package com.ahmad.assessmenttestfrontendmandiri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ahmad.assessmenttestfrontendmandiri.destinations.MoviesByGenreScreenDestination
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.MoviesByGenreScreen
import com.ahmad.assessmenttestfrontendmandiri.ui.theme.AssessmentTestFrontEndMandiriTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssessmentTestFrontEndMandiriTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}