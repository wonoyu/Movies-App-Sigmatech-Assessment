@file:OptIn(ExperimentalMaterialApi::class)

package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmad.assessmenttestfrontendmandiri.R
import com.ahmad.assessmenttestfrontendmandiri.core.reusable_compose.CustomTopAppBar
import com.ahmad.assessmenttestfrontendmandiri.destinations.MovieDetailsScreenDestination
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.MoviesByGenreViewModel
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.OnBottomReached
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun MovieReviewsScreen(
    navigator: DestinationsNavigator,
    movieId: Int,
) {
    val viewModel: MovieReviewsViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    var currentPage: String = "1"

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is MovieReviewsViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    DisposableEffect(key1 = viewModel) {
        viewModel.onGetMovieReviews(id = movieId, page = currentPage)
        onDispose {  }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                onClick = { navigator.popBackStack() },
                title = "Movie Reviews"
            )
        }
    ) {
        LazyColumn(
            state = scrollState
        ) {
            val movieReviews = state.movieReviews
           if (movieReviews != null) {
               val movieReviewsData = movieReviews.results
               items(movieReviewsData.size) { i ->
                   val movieReview = movieReviewsData[i]
                   val reviewerDetail = movieReview.author_details
                   Card(
                       onClick = {
//                           navigator.navigate(MovieDetailsScreenDestination(movie.id.toString()))
                       },
                       modifier = Modifier
                           .clip(RoundedCornerShape(10.dp))
                           .padding(16.dp)
                   ) {
                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           modifier = Modifier.fillMaxWidth()
                       ) {
                           if (reviewerDetail.avatar_path == null) {
                               Icon(
                                   imageVector = Icons.Default.AccountCircle,
                                   contentDescription = "image_not_found",
                                   modifier = Modifier.size(100.dp)
                               )
                           }
                           if (reviewerDetail.avatar_path != null) {
                               AsyncImage(
                                   model = ImageRequest.Builder(LocalContext.current)
                                       .placeholder(R.drawable.placeholder_image)
                                       .data("https://image.tmdb.org/t/p/w500${reviewerDetail.avatar_path}")
                                       .crossfade(true)
                                       .build(),
                                   contentDescription = null,
                                   contentScale = ContentScale.Crop,
                                   modifier = Modifier
                                       .size(100.dp)
                                       .clip(CircleShape)
                               )
                           }
                           Spacer(modifier = Modifier.width(16.dp))
                           Column(
                               verticalArrangement = Arrangement.SpaceBetween,
                               modifier = Modifier.fillMaxHeight()
                           ) {
                               Text(
                                   text = "Name: ${reviewerDetail.name}",
                                   fontWeight = FontWeight.Bold,
                               )
                               Spacer(modifier = Modifier.height(4.dp))
                               Text(
                                   text = "Username: ${reviewerDetail.username}",
                                   fontWeight = FontWeight.Bold,
                               )
                               Spacer(modifier = Modifier.height(24.dp))
                               Text(
                                   text = "Rated ${reviewerDetail.rating} out of 5",
                               )
                           }
                       }
                       Divider()
                   }
               }
           }

        }

        scrollState.OnBottomReached {
            Log.d("", "Fetched")
        }
    }
}