@file:OptIn(ExperimentalMaterialApi::class)

package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmad.assessmenttestfrontendmandiri.R
import com.ahmad.assessmenttestfrontendmandiri.core.reusable_compose.CustomTopAppBar
import com.ahmad.assessmenttestfrontendmandiri.core.reusable_compose.RatingBar
import com.ahmad.assessmenttestfrontendmandiri.destinations.MovieReviewsScreenDestination
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.MoviesByGenreViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt

@Destination
@Composable
fun MovieDetailsScreen(
    navigator: DestinationsNavigator,
    movieId: String,
) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val contextForToast = LocalContext.current.applicationContext
    val configuration = LocalConfiguration.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MovieDetailsViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onGetMovieDetails(id = parseInt(movieId))
        coroutineScope.launch {
            scaffoldState.bottomSheetState.expand()
        }
        onDispose {}
    }

    Box() {
        val movieDetails = state.movieDetails
        if (movieDetails != null) {
            if (movieDetails.poster_path != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .placeholder(R.drawable.placeholder_image)
                        .data("https://image.tmdb.org/t/p/original${movieDetails.poster_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxHeight()
                )
            }
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                backgroundColor = Color.Transparent,
                sheetContent = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth()
                    ) {
                        Card(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(24.dp),
                            contentColor = MaterialTheme.colors.onBackground,
                            backgroundColor = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .height(10.dp)
                                .width(100.dp)
                        ) {

                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .height((configuration.screenHeightDp * 0.75).dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            val genres = movieDetails.genres.map { it.name }
                            val genreNames = genres.joinToString(", ")
                            val languages = movieDetails.spoken_languages.map { it.english_name }
                            val languageNames = languages.joinToString(", ")
                            val hours = (movieDetails.runtime / 60).toInt()
                            val minutes = movieDetails.runtime % 60
                            Text(
                                text = movieDetails.title,
                                fontSize = MaterialTheme.typography.h6.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Released Date: ${movieDetails.release_date}")
                            if (languageNames.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Languages: ${languageNames}")
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = genreNames)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "${hours}h ${minutes}m")
                            Spacer(modifier = Modifier.height(8.dp))
                            RatingBar(rating = (movieDetails.vote_average / 2).toFloat())
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "(Rated: ${(movieDetails.vote_average / 2).toFloat()}, ${movieDetails.vote_count} Users)")
                            Spacer(modifier = Modifier.height(24.dp))
                            if (movieDetails.tagline.isNotBlank()) {
                                Text(
                                    text = "Tagline",
                                    fontSize = MaterialTheme.typography.h6.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = movieDetails.tagline)
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                            Text(
                                text = "Overview",
                                fontSize = MaterialTheme.typography.h6.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = movieDetails.overview)
                            Spacer(modifier = Modifier.height(32.dp))
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        navigator.navigate(MovieReviewsScreenDestination(movieId = movieDetails.id))
                                    }
                                ) {
                                    Text(text = "Movie Reviews")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {  }) {
                                    Text(text = "Movie Trailer")
                                }
                            }
                        }
                    }
                },
                floatingActionButton = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FloatingActionButton(
                            onClick = { navigator.popBackStack() },
                            modifier = Modifier.padding(start = 32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Button"
                            )
                        }
                        FloatingActionButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (scaffoldState.bottomSheetState.isExpanded) {
                                        scaffoldState.bottomSheetState.collapse()
                                    } else {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                        ) {
                            if (scaffoldState.bottomSheetState.isExpanded) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Down Button"
                                )
                            }
                            if (scaffoldState.bottomSheetState.isCollapsed) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Up Button"
                                )
                            }
                        }
                    }
                }
            ) {

            }
        }
        if (state.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}