@file:OptIn(ExperimentalMaterialApi::class)

package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.presentation

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmad.assessmenttestfrontendmandiri.core.reusable_compose.CustomTopAppBar
import com.ahmad.assessmenttestfrontendmandiri.destinations.MovieDetailsScreenDestination
import com.ahmad.assessmenttestfrontendmandiri.destinations.WebBrowserDestination
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation.MovieDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun MovieTrailerScreen(
    navigator: DestinationsNavigator,
    movieId: Int,
) {
    val viewModel: MovieTrailerViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    val uriHandler = LocalUriHandler.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MovieTrailerViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onGetMovieTrailer(id = movieId)
        onDispose {  }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar(
                onClick = { navigator.popBackStack() },
                title = "Movie Trailer"
            )
        }
    ) {
        if (state.movieTrailer != null && state.movieTrailer.results.isNotEmpty()) {
            val movieTrailers = state.movieTrailer.results
            LazyColumn(
                state = scrollState
            ) {
                items(movieTrailers.size) { i ->
                    val trailer = movieTrailers[i]
                    Card(
                        onClick = {
                                  uriHandler.openUri("https://www.youtube.com/watch?v=${trailer.key}")
                            // navigator.navigate(WebBrowserDestination(mUrl = trailer.key))
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .padding(16.dp)
                    ) {
                        ListItem(
                            secondaryText = {
                                Text(text = "${trailer.type} | ${trailer.site}")
                            }
                        ) {
                            Text(
                                text = trailer.name,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Divider()
                    }
                }

            }
        }
        if (state.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Destination
@Composable
fun WebBrowser(mUrl: String) {
    val url = "https://www.youtube.com/watch?v=$mUrl"
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        }, update = {
            it.loadUrl(url)
        })
    }
}