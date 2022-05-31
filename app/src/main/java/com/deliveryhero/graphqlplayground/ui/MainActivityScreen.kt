package com.deliveryhero.graphqlplayground.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deliveryhero.graphqlplayground.R
import com.deliveryhero.graphqlplayground.domain.LaunchItem
import com.deliveryhero.graphqlplayground.ui.theme.GraphQlPlaygroundTheme


@Composable
fun MainActivityScreen(viewModel: MainViewModel = viewModel()) {
    GraphQlPlaygroundTheme {
        val scaffoldState = rememberScaffoldState()
        viewModel.uiState.errorMessage?.let {
            LaunchedEffect(scaffoldState.snackbarHostState) {
                val result = scaffoldState.snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = viewModel.uiState.errorActionMessage
                )
                when (result) {
                    SnackbarResult.Dismissed -> {
                        // setShowSnackBar(false)
                    }
                    SnackbarResult.ActionPerformed -> {
                        // setShowSnackBar(false)
                        viewModel.uiState.errorAction?.invoke()
                    }
                }
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                CustomSnackbar(snackbarHostState = scaffoldState.snackbarHostState)
            },
            backgroundColor = MaterialTheme.colors.background
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (viewModel.uiState.loading) {
                    CircularProgressIndicator()
                } else {
                    LaunchListView(viewModel.uiState.data)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchListViewEmpty() {
    LaunchListView(emptyList())
}

@Preview(showBackground = true)
@Composable
fun LaunchListView(
    list: List<LaunchItem> = listOf(
        LaunchItem("000000001", "site 1", booked = true, missionName = "Starlink 1"),
        LaunchItem("000000002", "site 2", booked = false, missionName = "Starlink 2"),
    )
) {
    if (list.isNotEmpty()) {
        LazyColumn {
            items(
                items = list,
                itemContent = {
                    LaunchItemView(launchItem = it)
                }
            )
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier
                    .size(150.dp)
                    .alpha(0.2f),
                imageVector = Icons.Rounded.Warning,
                contentDescription = "Empty List"
            )
            Text(text = "No Launch information has been found!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchItemView(
    modifier: Modifier = Modifier,
    launchItem: LaunchItem = LaunchItem(
        "123456789",
        "site",
        booked = true,
        missionName = "Starlink",
        missionPatch = "https://images2.imgbox.com/9a/96/nLppz9HW_o.png"
    ),
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(launchItem.missionPatch)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_outline_image),
                error = painterResource(R.drawable.ic_outline_image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "${launchItem.missionName}", fontWeight = FontWeight.Bold)
                Text(text = "Site: ${launchItem.site}")
                Text(
                    text = if (launchItem.booked) "Status: Booked" else "Status: Available"
                )
            }
        }
    }
}