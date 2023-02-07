package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmad.assessmenttestfrontendmandiri.destinations.MoviesByGenreScreenDestination
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.MoviesByGenreViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Destination(start = true)
@Composable
fun MovieGenresScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: MovieGenresViewModel = hiltViewModel()
    val moviesByGenreVM: MoviesByGenreViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MovieGenresViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar() {
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Movies App")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.movieGenres.size) { i ->
                val genre = state.movieGenres[i]
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        Log.d("tags", genre.id.toString())
                        navigator.navigate(MoviesByGenreScreenDestination(
                            genre = genre.name,
                            genreId = genre.id.toString(),
                            page = "1"
                        ))
                    },
                ) {
                    Text(text = genre.name, modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        )
                }
                if (i < state.movieGenres.size - 1) {
                    Divider()
                }
            }
        }
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}