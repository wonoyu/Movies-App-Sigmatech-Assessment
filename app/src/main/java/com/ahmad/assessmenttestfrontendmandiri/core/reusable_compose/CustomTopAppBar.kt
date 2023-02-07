package com.ahmad.assessmenttestfrontendmandiri.core.reusable_compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomTopAppBar(
    onClick: () -> Unit,
    title: String,
    appBarColor: Color = MaterialTheme.colors.primarySurface,
    elevation: Dp = AppBarDefaults.TopAppBarElevation
) {
    TopAppBar(
        backgroundColor = appBarColor,
        elevation = elevation,
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title)
    }
}