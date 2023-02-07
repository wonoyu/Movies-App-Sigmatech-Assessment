@file:OptIn(ExperimentalMaterialApi::class)

package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHost
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ahmad.assessmenttestfrontendmandiri.R
import com.ahmad.assessmenttestfrontendmandiri.core.reusable_compose.CustomTopAppBar
import com.ahmad.assessmenttestfrontendmandiri.core.reusable_compose.RatingBar
import com.ahmad.assessmenttestfrontendmandiri.destinations.MovieDetailsScreenDestination
import com.ahmad.assessmenttestfrontendmandiri.destinations.MoviesByGenreScreenDestination
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.Genre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.presentation.MovieGenresViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt

@Destination
@Composable
fun MoviesByGenreScreen(
    navigator: DestinationsNavigator,
    genre: String,
    genreId: String,
    page: String,
) {
    val viewModel: MoviesByGenreViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    var currentPage: Int = parseInt(page)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MoviesByGenreViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onGetMoviesByGenre(genre = genreId, page = page)
        onDispose {}
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar(
                onClick = {
                    navigator.popBackStack()
                },
                title = "Movies with $genre Genre"
            )
        }
    ) {
        LazyColumn(
            state = scrollState
        ) {
            if (state.moviesByGenre != null) {
                items(state.moviesByGenre.results.size) { i ->
                    val movie = state.moviesByGenre.results[i]
                    Card(
                        onClick = {
                                  navigator.navigate(MovieDetailsScreenDestination(movie.id.toString()))
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (movie.backdrop_path == null) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "image_not_found",
                                        modifier = Modifier.size(150.dp)
                                    )
                                    Text(text = "Image Not Found")
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                            if (movie.poster_path != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .placeholder(R.drawable.placeholder_image)
                                        .data("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Text(
                                    text = movie.title,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = movie.overview,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }
                        Divider()
                    }
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

        scrollState.OnBottomReached {
            if (currentPage < (state.moviesByGenre?.total_pages ?: 1)) {
                currentPage++
                viewModel.onLoadMore(genre = genreId, page = currentPage.toString())
            }
        }
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                return@derivedStateOf false

            lastVisibleItem.index == layoutInfo.totalItemsCount - 4
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMore()
            }
    }
}