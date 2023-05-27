package dev.raju.consumrz.ui.screens.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.raju.consumrz.ui.navigation.NavRoute
import dev.raju.consumrz.ui.theme.ConsumrzTheme

/**
 * Created by Rajashekhar Vanahalli on 25 May, 2023
 */

object PostsRoute : NavRoute<PostsViewModel> {

    override val route = "posts"

    @Composable
    override fun viewModel(): PostsViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: PostsViewModel) = PostsScreen(viewModel)
}

@Composable
fun PostsScreen(
    viewModel: PostsViewModel
) {
    PostsComponent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostsComponent() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Posts Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConsumrzTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PostsComponent()
        }
    }
}